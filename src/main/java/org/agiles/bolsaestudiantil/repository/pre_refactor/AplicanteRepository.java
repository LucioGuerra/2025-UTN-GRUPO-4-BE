package org.agiles.bolsaestudiantil.repository.pre_refactor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AplicanteRepository extends JpaRepository<AplicanteEntity, Long>, JpaSpecificationExecutor<AplicanteEntity> {
}
