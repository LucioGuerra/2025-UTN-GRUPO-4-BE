package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.AplicanteDTO;
import org.agiles.bolsaestudiantil.dto.response.AplicanteListaDTO;
import org.agiles.bolsaestudiantil.service.AplicanteService;
import org.springframework.http.ResponseEntity;
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
}
