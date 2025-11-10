package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.OfferVoteResponseDTO;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.entity.OfferVoteEntity;
import org.agiles.bolsaestudiantil.entity.UserEntity;
import org.agiles.bolsaestudiantil.repository.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferVoteService {

    @PersistenceContext
    private final EntityManager entityManager;
    
    private final OfferRepository offerRepository;
    private final UserService userService;

    @Transactional
    public OfferVoteResponseDTO addPositiveVote(Long offerId, String keycloakId) {
        return addVote(offerId, keycloakId, true);
    }

    @Transactional
    public OfferVoteResponseDTO addNegativeVote(Long offerId, String keycloakId) {
        return addVote(offerId, keycloakId, false);
    }

    @Transactional
    public OfferVoteResponseDTO removeVote(Long offerId, String keycloakId) {
        OfferEntity offer = getOfferEntity(offerId);
        UserEntity user = userService.getUserEntityByKeycloakId(keycloakId);

        Optional<OfferVoteEntity> existingVote = findVoteByOfferAndUser(offer, user);

        if (existingVote.isPresent()) {
            OfferVoteEntity vote = existingVote.get();
            
            // Actualizar contadores
            if (vote.getIsPositive()) {
                offer.setPositiveVotes(offer.getPositiveVotes() - 1);
            } else {
                offer.setNegativeVotes(offer.getNegativeVotes() - 1);
            }

            // Eliminar el voto
            entityManager.remove(vote);
            entityManager.flush();
            
            offerRepository.save(offer);
        }

        return buildVoteResponse(offer, null);
    }

    @Transactional(readOnly = true)
    public OfferVoteResponseDTO getOfferVotes(Long offerId, String keycloakId) {
        OfferEntity offer = getOfferEntity(offerId);
        
        Boolean userVote = null;
        if (keycloakId != null) {
            try {
                UserEntity user = userService.getUserEntityByKeycloakId(keycloakId);
                Optional<OfferVoteEntity> existingVote = findVoteByOfferAndUser(offer, user);
                userVote = existingVote.map(OfferVoteEntity::getIsPositive).orElse(null);
            } catch (EntityNotFoundException e) {
                // Usuario no encontrado, userVote queda null
            }
        }

        return buildVoteResponse(offer, userVote);
    }

    private OfferVoteResponseDTO addVote(Long offerId, String keycloakId, boolean isPositive) {
        OfferEntity offer = getOfferEntity(offerId);
        UserEntity user = userService.getUserEntityByKeycloakId(keycloakId);

        Optional<OfferVoteEntity> existingVote = findVoteByOfferAndUser(offer, user);

        if (existingVote.isPresent()) {
            OfferVoteEntity vote = existingVote.get();
            
            // Si el voto es el mismo, no hacer nada
            if (vote.getIsPositive().equals(isPositive)) {
                return buildVoteResponse(offer, isPositive);
            }

            // Si el voto es diferente, actualizar
            if (vote.getIsPositive()) {
                offer.setPositiveVotes(offer.getPositiveVotes() - 1);
                offer.setNegativeVotes(offer.getNegativeVotes() + 1);
            } else {
                offer.setNegativeVotes(offer.getNegativeVotes() - 1);
                offer.setPositiveVotes(offer.getPositiveVotes() + 1);
            }

            vote.setIsPositive(isPositive);
            entityManager.merge(vote);
        } else {
            // Crear nuevo voto
            OfferVoteEntity newVote = new OfferVoteEntity();
            newVote.setOffer(offer);
            newVote.setUser(user);
            newVote.setIsPositive(isPositive);

            if (isPositive) {
                offer.setPositiveVotes(offer.getPositiveVotes() + 1);
            } else {
                offer.setNegativeVotes(offer.getNegativeVotes() + 1);
            }

            entityManager.persist(newVote);
        }

        entityManager.flush();
        offerRepository.save(offer);

        return buildVoteResponse(offer, isPositive);
    }

    private Optional<OfferVoteEntity> findVoteByOfferAndUser(OfferEntity offer, UserEntity user) {
        return entityManager.createQuery(
                "SELECT v FROM OfferVoteEntity v WHERE v.offer = :offer AND v.user = :user", 
                OfferVoteEntity.class)
                .setParameter("offer", offer)
                .setParameter("user", user)
                .getResultList()
                .stream()
                .findFirst();
    }

    private OfferEntity getOfferEntity(Long offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + offerId));
    }

    private OfferVoteResponseDTO buildVoteResponse(OfferEntity offer, Boolean userVote) {
        OfferVoteResponseDTO response = new OfferVoteResponseDTO();
        response.setPositiveVotes(offer.getPositiveVotes());
        response.setNegativeVotes(offer.getNegativeVotes());
        response.setTotalScore(offer.getTotalScore());
        response.setUserVote(userVote);
        return response;
    }
}
