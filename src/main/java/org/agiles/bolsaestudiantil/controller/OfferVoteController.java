package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.OfferVoteResponseDTO;
import org.agiles.bolsaestudiantil.service.OfferVoteService;
import org.agiles.bolsaestudiantil.util.AuthenticationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/offers/{offerId}/votes")
@RequiredArgsConstructor
public class OfferVoteController {

    private final OfferVoteService offerVoteService;

    @PostMapping("/like")
    public ResponseEntity<OfferVoteResponseDTO> addLike(@PathVariable Long offerId) {
        String keycloakId = AuthenticationUtil.getKeycloakIdFromAuthentication();
        OfferVoteResponseDTO response = offerVoteService.addPositiveVote(offerId, keycloakId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dislike")
    public ResponseEntity<OfferVoteResponseDTO> addDislike(@PathVariable Long offerId) {
        String keycloakId = AuthenticationUtil.getKeycloakIdFromAuthentication();
        OfferVoteResponseDTO response = offerVoteService.addNegativeVote(offerId, keycloakId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<OfferVoteResponseDTO> removeVote(@PathVariable Long offerId) {
        String keycloakId = AuthenticationUtil.getKeycloakIdFromAuthentication();
        OfferVoteResponseDTO response = offerVoteService.removeVote(offerId, keycloakId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<OfferVoteResponseDTO> getVotes(@PathVariable Long offerId) {
        String keycloakId = AuthenticationUtil.getKeycloakIdFromAuthentication();
        OfferVoteResponseDTO response = offerVoteService.getOfferVotes(offerId, keycloakId);
        return ResponseEntity.ok(response);
    }
}
