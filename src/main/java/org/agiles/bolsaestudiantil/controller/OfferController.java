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

    @PostMapping("/{offer_id}/apply")
    public ResponseEntity<OfferDTO> applyToOffer(@RequestParam Long student_id, @PathVariable Long offer_id){
        return ResponseEntity.status(HttpStatus.OK).body(offerService.applyToOffer( offer_id, student_id));
    }
}
