package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.MateriaEntity;
import org.agiles.bolsaestudiantil.entity.MateriaXAplicanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaXAplicanteRepository extends JpaRepository<MateriaXAplicanteEntity, Long> {
}
