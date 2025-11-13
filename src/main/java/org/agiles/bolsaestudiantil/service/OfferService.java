package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.internal.OfferFilter;
import org.agiles.bolsaestudiantil.dto.request.OfferRequestDTO;
import org.agiles.bolsaestudiantil.dto.request.update.OfferUpdateRequestDTO;
import org.agiles.bolsaestudiantil.entity.*;
import org.agiles.bolsaestudiantil.dto.response.apply.ApplyForOfferResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.student.StudentSummaryResponseDTO;
import org.agiles.bolsaestudiantil.mapper.OfferMapper;
import org.agiles.bolsaestudiantil.mapper.StudentMapper;
import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.repository.OfferRepository;
import org.agiles.bolsaestudiantil.specification.OfferSpecification;
import org.agiles.bolsaestudiantil.util.AuthenticationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final AttributeService attributeService;
    private final UserService userService;
    private final OfferMapper offerMapper;
    private final StudentMapper studentMapper;
    private final OfferVoteService offerVoteService;

    public OfferResponseDTO createOffer(OfferRequestDTO request) {
        OfferEntity entity = mapToEntity(request);
        OfferEntity saved = offerRepository.save(entity);
        OfferResponseDTO dto = offerMapper.toResponseDTO(saved);
        dto.setApplyList(mapApplyList(saved.getApplyList()));
        return dto;
    }

    public OfferResponseDTO getOfferById(Long id) {
        OfferEntity entity = getOfferEntityById(id);
        OfferResponseDTO dto = offerMapper.toResponseDTO(entity);
        dto.setApplyList(mapApplyList(entity.getApplyList()));
        enrichWithVoteInfo(dto, id);
        return dto;
    }

    public Page<OfferResponseDTO> getAllOffers(OfferFilter filter, Pageable pageable) {
        Page<OfferEntity> offers = offerRepository.findAll(OfferSpecification.withFilters(filter), pageable);
        return offers.map(entity -> {
            OfferResponseDTO dto = offerMapper.toResponseDTO(entity);
            dto.setApplyList(mapApplyList(entity.getApplyList()));
            enrichWithVoteInfo(dto, entity.getId());
            return dto;
        });
    }

    public Page<OfferResponseDTO> getRecommendedOffers(Pageable pageable) {
        String keycloackId = AuthenticationUtil.getKeycloakIdFromAuthentication();
        if (keycloackId == null) {
            return getAllOffers(new OfferFilter(), pageable);
        }

        UserEntity user = userService.getUserEntityByKeycloakId(keycloackId);
        if (!(user instanceof UserEntity)) {
            return getAllOffers(new OfferFilter(), pageable);
        }

        StudentEntity student = (StudentEntity) user;
        List<AttributeEntity> studentAttributes = student.getAttributes();

        List<OfferEntity> allOffers = offerRepository.findAll();

        Map<OfferEntity, Double> offerScores = new HashMap<>();

        final double PESO_VOTO = 1.0;
        final double PESO_ATRIBUTO = 5.0;

        for (OfferEntity offer : allOffers) {
            double voteScore = offer.getTotalScore() * PESO_VOTO;
            double matchScore = calculateMatchScore(studentAttributes, offer.getAttributes()) * PESO_ATRIBUTO;
            double finalScore = voteScore + matchScore;
            offerScores.put(offer, finalScore);
        }

        List<OfferEntity> sortedOffers = allOffers.stream()
                .sorted((o1, o2) -> offerScores.get(o2).compareTo(offerScores.get(o1)))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sortedOffers.size());

        List<OfferEntity> pageContent = (start > end) ? List.of() : sortedOffers.subList(start, end);

        List<OfferResponseDTO> dtoList = pageContent.stream()
                .map(entity -> {
                    OfferResponseDTO dto = offerMapper.toResponseDTO(entity);
                    dto.setApplyList(mapApplyList(entity.getApplyList()));
                    enrichWithVoteInfo(dto, entity.getId());
                    return dto;
                })
                .toList();

        return new PageImpl<>(dtoList, pageable, sortedOffers.size());
    }

    private long calculateMatchScore(List<AttributeEntity> studentAttributes, List<AttributeEntity> offerAttributes) {
        if (studentAttributes == null || offerAttributes == null || studentAttributes.isEmpty() || offerAttributes.isEmpty()) {
            return 0;
        }

        var studentAttrNames = studentAttributes.stream()
                .map(AttributeEntity::getName)
                .collect(Collectors.toSet());

        var offerAttrNames = offerAttributes.stream()
                .map(AttributeEntity::getName)
                .collect(Collectors.toSet());

        studentAttrNames.retainAll(offerAttrNames);

        return studentAttrNames.size();
    }

    public OfferResponseDTO updateOffer(Long id, OfferUpdateRequestDTO request) {
        OfferEntity entity = getOfferEntityById(id);
        updateEntityFromUpdateDTO(entity, request);
        OfferEntity saved = offerRepository.save(entity);
        OfferResponseDTO dto = offerMapper.toResponseDTO(saved);
        dto.setApplyList(mapApplyList(saved.getApplyList()));
        return dto;
    }

    public void deleteOffer(Long id) {
        if (!offerRepository.existsById(id)) {
            throw new EntityNotFoundException("Offer not found with id: " + id);
        }
        offerRepository.deleteById(id);
    }

    public OfferEntity getOfferEntityById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + id));
    }

    private OfferEntity mapToEntity(OfferRequestDTO request) {
        OfferEntity entity = new OfferEntity();
        updateEntityFromDTO(entity, request);
        return entity;
    }

    private void updateEntityFromDTO(OfferEntity entity, OfferRequestDTO request) {
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setRequirements(request.getRequirements());
        entity.setModality(request.getModality());
        entity.setLocation(request.getLocation());
        entity.setEstimatedPayment(request.getEstimatedPayment());

        if (request.getBidderId() != null) {
            UserEntity bidder = userService.getUserById(request.getBidderId());
            entity.setBidder(bidder);
        }

        if (request.getAttributes() != null) {
            List<AttributeEntity> attributes = request.getAttributes().stream()
                    .map(attributeService::findOrCreateAttribute)
                    .toList();
            entity.setAttributes(attributes);
        }
    }

    private void updateEntityFromUpdateDTO(OfferEntity entity, OfferUpdateRequestDTO request) {
        if (request.getTitle() != null) entity.setTitle(request.getTitle());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        if (request.getRequirements() != null) entity.setRequirements(request.getRequirements());
        if (request.getModality() != null) entity.setModality(request.getModality());
        if (request.getLocation() != null) entity.setLocation(request.getLocation());
        if (request.getEstimatedPayment() != null) entity.setEstimatedPayment(request.getEstimatedPayment());
        
        if (request.getAttributes() != null) {
            entity.getAttributes().clear();
            List<AttributeEntity> newAttributes = request.getAttributes().stream()
                    .map(attributeService::findOrCreateAttribute)
                    .toList();
            entity.getAttributes().addAll(newAttributes);
        }
    }

    private List<ApplyForOfferResponseDTO> mapApplyList(List<ApplyEntity> applies) {
        return applies.stream()
                .map(apply -> {
                    ApplyForOfferResponseDTO dto = new ApplyForOfferResponseDTO();
                    dto.setId(apply.getId());
                    dto.setCustomCoverLetter(apply.getCustomCoverLetter());
                    dto.setStudent(studentMapper.toSummaryDTO(apply.getStudent()));
                    return dto;
                })
                .toList();
    }

    private void enrichWithVoteInfo(OfferResponseDTO dto, Long offerId) {
        try {
            String keycloakId = org.agiles.bolsaestudiantil.util.AuthenticationUtil.getKeycloakIdFromAuthentication();
            org.agiles.bolsaestudiantil.dto.response.OfferVoteResponseDTO voteInfo = offerVoteService.getOfferVotes(offerId, keycloakId);
            dto.setPositiveVotes(voteInfo.getPositiveVotes());
            dto.setNegativeVotes(voteInfo.getNegativeVotes());
            dto.setTotalScore(voteInfo.getTotalScore());
            dto.setUserVote(voteInfo.getUserVote());
        } catch (Exception ex) {
            if (ex instanceof EntityNotFoundException entityNotFound) {
                throw entityNotFound;
            }
            throw new EntityNotFoundException("Informacion de votos de dicha oferta no disponible");
        }
    }
}
