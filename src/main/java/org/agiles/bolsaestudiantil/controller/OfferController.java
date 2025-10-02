package org.agiles.bolsaestudiantil.controller;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.ApplyDTO;
import org.agiles.bolsaestudiantil.dto.request.OfferRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    @PostMapping
    public ResponseEntity<OfferResponseDTO> createOffer(@RequestBody OfferRequestDTO requestDTO){
        OfferResponseDTO createdOffer = offerService.createOffer(requestDTO);
        return new ResponseEntity<>(createdOffer, HttpStatus.CREATED);
    }

    @PostMapping("/{offerId}/apply")
    public ResponseEntity<Void> applyToOffer(@RequestBody ApplyDTO applyDTO, @PathVariable Long offerId){
        offerService.applyToOffer(offerId, applyDTO.getStudentId(), applyDTO.getCoverLetter());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
