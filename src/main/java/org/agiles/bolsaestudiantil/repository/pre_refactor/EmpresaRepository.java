package org.agiles.bolsaestudiantil.repository.pre_refactor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
    List<EmpresaEntity> findByNombreContainingIgnoreCase(String nombre);
}
