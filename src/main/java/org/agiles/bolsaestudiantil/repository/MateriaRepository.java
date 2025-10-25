package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
    Optional<MateriaEntity> findByCodigo(String codigo);
}
