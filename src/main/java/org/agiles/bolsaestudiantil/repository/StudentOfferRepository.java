package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.StudentOfferEntity;
import org.agiles.bolsaestudiantil.entity.StudentOfferId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentOfferRepository extends JpaRepository<StudentOfferEntity, StudentOfferId> {
}
