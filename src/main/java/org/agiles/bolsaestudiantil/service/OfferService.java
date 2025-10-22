package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.OfferRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.ApplyResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.entity.AttributeEntity;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.entity.UserEntity;
import org.agiles.bolsaestudiantil.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final AttributeService attributeService;
    private final UserService userService;


    public OfferEntity getOfferEntityById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + id));
    }


    public OfferResponseDTO createOffer(OfferRequestDTO offerRequestDTO) {
        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setTitle(offerRequestDTO.getTitle());
        offerEntity.setDescription(offerRequestDTO.getDescription());
        offerEntity.setLocation(offerRequestDTO.getLocation());
        offerEntity.setRequirements(offerRequestDTO.getRequirements());
        offerEntity.setEstimatedPayment(offerRequestDTO.getEstimatedPayment());
        offerEntity.setModality(offerRequestDTO.getModality());

        List<AttributeEntity> attributes = offerRequestDTO.getAttributes().stream()
                .map(attributeService::createAttribute)
                .toList();

        UserEntity bidder = userService.getUserById(offerRequestDTO.getBidderId());
        offerEntity.setBidder(bidder);

        offerEntity.setAttributes(attributes);


        OfferEntity savedOffer = offerRepository.save(offerEntity);

        return new OfferResponseDTO(
                savedOffer.getId(),
                savedOffer.getTitle(),
                savedOffer.getDescription(),
                savedOffer.getLocation(),
                savedOffer.getEstimatedPayment(),
                savedOffer.getRequirements(),
                savedOffer.getModality(),
                null,
                userService.userToDTO(savedOffer.getBidder())
        );

    }
}
