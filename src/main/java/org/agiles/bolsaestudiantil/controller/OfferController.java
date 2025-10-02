package org.agiles.bolsaestudiantil.controller;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.OfferDTO;
import org.agiles.bolsaestudiantil.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    @PostMapping("/{offerId}/apply")
    public ResponseEntity<OfferDTO> applyToOffer(@RequestBody Long studentId, @RequestBody(required = false) String coverLetter, @PathVariable Long offerId){
        return ResponseEntity.status(HttpStatus.OK).body(offerService.applyToOffer(offerId, studentId, coverLetter));
    }
}
