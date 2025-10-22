package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.repository.OfferRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;


    public OfferEntity getOfferEntityById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + id));
    }
}
