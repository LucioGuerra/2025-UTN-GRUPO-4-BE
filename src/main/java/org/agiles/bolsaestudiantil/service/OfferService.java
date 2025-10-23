package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.internal.OfferFilter;
import org.agiles.bolsaestudiantil.dto.request.OfferRequestDTO;
import org.agiles.bolsaestudiantil.dto.request.update.OfferUpdateRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.apply.ApplyForOfferResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.student.StudentSummaryResponseDTO;
import org.agiles.bolsaestudiantil.mapper.OfferMapper;
import org.agiles.bolsaestudiantil.mapper.StudentMapper;
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
    private final OfferMapper offerMapper;
    private final StudentMapper studentMapper;

    public OfferResponseDTO createOffer(OfferRequestDTO request) {
        OfferEntity entity = mapToEntity(request);
        OfferEntity saved = offerRepository.save(entity);
        return offerMapper.toResponseDTO(saved);
    }

    public OfferResponseDTO getOfferById(Long id) {
        OfferEntity entity = getOfferEntityById(id);
        return offerMapper.toResponseDTO(entity);
    }

    public Page<OfferResponseDTO> getAllOffers(OfferFilter filter, Pageable pageable) {
        Page<OfferEntity> offers = offerRepository.findAll(OfferSpecification.withFilters(filter), pageable);
        return offers.map(offerMapper::toResponseDTO);
    }

    public OfferResponseDTO updateOffer(Long id, OfferUpdateRequestDTO request) {
        OfferEntity entity = getOfferEntityById(id);
        updateEntityFromUpdateDTO(entity, request);
        OfferEntity saved = offerRepository.save(entity);
        return offerMapper.toResponseDTO(saved);
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
            List<AttributeEntity> attributes = request.getAttributes().stream()
                    .map(attributeService::findOrCreateAttribute)
                    .toList();
            entity.setAttributes(attributes);
        }
    }


}
