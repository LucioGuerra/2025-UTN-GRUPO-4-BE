package org.agiles.bolsaestudiantil.repository.pre_refactor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// Cambiamos asi usa el OfertaSpecification
// y no tenemos que hacer consultas dinamicas en el service
@Repository
public interface OfertaRepository extends JpaRepository<OfertaEntity, Long>, JpaSpecificationExecutor<OfertaEntity> {
}
