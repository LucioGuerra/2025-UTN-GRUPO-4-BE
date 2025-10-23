package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.internal.OfferFilter;
import org.agiles.bolsaestudiantil.dto.request.OfferRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.ApplyResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.entity.AttributeEntity;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.entity.UserEntity;
import org.agiles.bolsaestudiantil.repository.OfferRepository;
import org.agiles.bolsaestudiantil.specification.OfferSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final AttributeService attributeService;
    private final UserService userService;

    public OfferResponseDTO createOffer(OfferRequestDTO request) {
        OfferEntity entity = mapToEntity(request);
        OfferEntity saved = offerRepository.save(entity);
        return mapToDTO(saved);
    }

    public OfferResponseDTO getOfferById(Long id) {
        OfferEntity entity = getOfferEntityById(id);
        return mapToDTO(entity);
    }

    public Page<OfferResponseDTO> getAllOffers(OfferFilter filter, Pageable pageable) {
        Page<OfferEntity> offers = offerRepository.findAll(OfferSpecification.withFilters(filter), pageable);
        return offers.map(this::mapToDTO);
    }

    public OfferResponseDTO updateOffer(Long id, OfferRequestDTO request) {
        OfferEntity entity = getOfferEntityById(id);
        updateEntityFromDTO(entity, request);
        OfferEntity saved = offerRepository.save(entity);
        return mapToDTO(saved);
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
                    .map(attributeService::createAttribute)
                    .toList();
            entity.setAttributes(attributes);
        }
    }

    private OfferResponseDTO mapToDTO(OfferEntity entity) {
        List<ApplyResponseDTO> applyDTOs = entity.getApplyList().stream()
                .map(apply -> {
                    ApplyResponseDTO dto = new ApplyResponseDTO();
                    dto.setCustomCoverLetter(apply.getCustomCoverLetter());
                    dto.setStudent(userService.mapToStudentDTO(apply.getStudent()));
                    return dto;
                })
                .toList();

        return new OfferResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getLocation(),
                entity.getEstimatedPayment(),
                entity.getRequirements(),
                entity.getModality(),
                applyDTOs,
                userService.userToDTO(entity.getBidder())
        );
    }
}
