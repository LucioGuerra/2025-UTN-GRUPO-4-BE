package org.agiles.bolsaestudiantil.repository.pre_refactor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AplicacionRepository extends JpaRepository<AplicacionEntity, Long> {
    @Query("SELECT a FROM AplicacionEntity a WHERE a.aplicante.id = :aplicanteId AND a.oferta.id = :ofertaId")
    Optional<AplicacionEntity> findByAplicanteAndOferta(@Param("aplicanteId") Long aplicanteId,
                                                         @Param("ofertaId") Long ofertaId);
    
    @Query("SELECT a FROM AplicacionEntity a WHERE a.oferta.id = :ofertaId")
    List<AplicacionEntity> findByOfertaId(@Param("ofertaId") Long ofertaId);
}
