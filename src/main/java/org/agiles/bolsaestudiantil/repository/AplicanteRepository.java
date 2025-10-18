package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.AplicanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AplicanteRepository extends JpaRepository<AplicanteEntity, Long> {
}
