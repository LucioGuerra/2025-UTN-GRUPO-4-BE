package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.ApplyDTO;
import org.agiles.bolsaestudiantil.dto.request.OfferFilterDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferListDTO;
import org.agiles.bolsaestudiantil.dto.response.PagedResponseDTO;
import org.agiles.bolsaestudiantil.dto.request.OfferRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    @GetMapping
    public ResponseEntity<PagedResponseDTO<OfferListDTO>> getOffers(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String contractType,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        OfferFilterDTO filters = new OfferFilterDTO();
        filters.setTitle(title);
        filters.setCompany(company);
        filters.setContractType(contractType);
        filters.setLocation(location);
        filters.setStatus(status);

        PagedResponseDTO<OfferListDTO> response = offerService.getOffers(filters, page, size);
        return ResponseEntity.ok(response);
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
