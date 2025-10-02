package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.StudentOfferEntity;
import org.agiles.bolsaestudiantil.entity.StudentOfferId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentOfferRepository extends JpaRepository<StudentOfferEntity, StudentOfferId> {
    @Query("SELECT so FROM StudentOfferEntity so WHERE so.student.id = :studentId AND so.offer.id = :offerId")
    Optional<StudentOfferEntity> findByStudentAndOffer(@Param("studentId") Long studentId,
                                                       @Param("offerId") Long offerId);
}
