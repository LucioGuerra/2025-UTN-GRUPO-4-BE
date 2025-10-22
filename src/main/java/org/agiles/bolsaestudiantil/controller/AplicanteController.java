package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.AplicanteFilterDTO;
import org.agiles.bolsaestudiantil.dto.request.ModAplicanteDTO;
import org.agiles.bolsaestudiantil.dto.response.AplicanteDTO;
import org.agiles.bolsaestudiantil.dto.response.AplicanteListaDTO;
import org.agiles.bolsaestudiantil.service.viejo.AplicanteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aplicantes")
@RequiredArgsConstructor
public class AplicanteController {

    private final AplicanteService aplicanteService;

    @GetMapping("/{id}")
    public ResponseEntity<AplicanteDTO> getById(@PathVariable Long id) {
        AplicanteDTO aplicante = aplicanteService.getAplicanteDTOById(id);
        return ResponseEntity.ok(aplicante);
    }
    
    @GetMapping("/oferta/{ofertaId}")
    public ResponseEntity<AplicanteListaDTO> getAplicantesPorOferta(@PathVariable Long ofertaId) {
        AplicanteListaDTO aplicantes = aplicanteService.getAplicantesPorOferta(ofertaId);
        return ResponseEntity.ok(aplicantes);
    }

    @GetMapping
    public Page<AplicanteEntity> listarAplicantes(
            AplicanteFilterDTO filter,
            Pageable pageable
    ) {
        return aplicanteService.obtenerAplicantes(filter, pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('Student')")
    public ResponseEntity<AplicanteDTO> actualizarAplicante(
            @PathVariable Long id,
            @RequestBody ModAplicanteDTO aplicanteDTO
    ) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
