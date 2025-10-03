package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// Cambiamos asi usa el OfferSpecification
// y no tenemos que hacer consultas dinamicas en el service
@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long>, JpaSpecificationExecutor<OfferEntity> {
}
