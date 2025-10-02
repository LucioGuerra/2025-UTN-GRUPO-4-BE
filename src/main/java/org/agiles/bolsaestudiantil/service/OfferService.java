package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.OfferDTO;
import org.agiles.bolsaestudiantil.repository.OfferRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferDTO applyToOffer(Long offerId, Long studentId) {
        return new OfferDTO();
    }
}
