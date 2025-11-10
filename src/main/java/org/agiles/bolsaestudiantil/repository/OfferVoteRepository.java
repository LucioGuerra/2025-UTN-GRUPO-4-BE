package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.entity.OfferVoteEntity;
import org.agiles.bolsaestudiantil.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferVoteRepository extends JpaRepository<OfferVoteEntity, Long> {
    
    Optional<OfferVoteEntity> findByOfferAndUser(OfferEntity offer, UserEntity user);
    
    boolean existsByOfferAndUser(OfferEntity offer, UserEntity user);
}
