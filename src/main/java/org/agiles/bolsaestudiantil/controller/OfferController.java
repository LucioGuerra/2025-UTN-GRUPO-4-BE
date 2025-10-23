package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.internal.OfferFilter;
import org.agiles.bolsaestudiantil.dto.request.OfferRequestDTO;
import org.agiles.bolsaestudiantil.dto.request.update.OfferUpdateRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.service.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PostMapping
    public ResponseEntity<OfferResponseDTO> createOffer(@RequestBody OfferRequestDTO request) {
        OfferResponseDTO response = offerService.createOffer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferResponseDTO> getOfferById(@PathVariable Long id) {
        OfferResponseDTO response = offerService.getOfferById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<OfferResponseDTO>> getAllOffers(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String requirements,
            @RequestParam(required = false) String modality,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String estimatedPayment,
            @RequestParam(required = false) Long bidderId,
            Pageable pageable) {
        
        OfferFilter filter = new OfferFilter();
        filter.setTitle(title);
        filter.setDescription(description);
        filter.setRequirements(requirements);
        filter.setModality(modality);
        filter.setLocation(location);
        filter.setEstimatedPayment(estimatedPayment);
        filter.setBidderId(bidderId);
        
        Page<OfferResponseDTO> response = offerService.getAllOffers(filter, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferResponseDTO> updateOffer(@PathVariable Long id, @RequestBody OfferUpdateRequestDTO request) {
        OfferResponseDTO response = offerService.updateOffer(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
        return ResponseEntity.noContent().build();
    }
}