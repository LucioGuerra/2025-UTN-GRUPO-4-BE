package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.AplicacionDTO;
import org.agiles.bolsaestudiantil.dto.request.OfertaFilterDTO;
import org.agiles.bolsaestudiantil.dto.request.OfertaRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.OfertaListaDTO;
import org.agiles.bolsaestudiantil.dto.response.OfertaResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.PagedResponseDTO;
import org.agiles.bolsaestudiantil.service.OfertaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    private final OfertaService ofertaService;

    @GetMapping
    public ResponseEntity<PagedResponseDTO<OfertaListaDTO>> getOfertas(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Long empresaId,
            @RequestParam(required = false) String tipoContrato,
            @RequestParam(required = false) String locacion,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        OfertaFilterDTO filters = new OfertaFilterDTO();
        filters.setTitulo(titulo);
        filters.setEmpresaId(empresaId);
        filters.setTipoContrato(tipoContrato);
        filters.setLocacion(locacion);
        filters.setStatus(status);

        PagedResponseDTO<OfertaListaDTO> response = ofertaService.getOfertas(filters, page, size, usuarioId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<OfertaResponseDTO> createOferta(@RequestBody OfertaRequestDTO requestDTO){
        OfertaResponseDTO createdOferta = ofertaService.createOferta(requestDTO);
        return new ResponseEntity<>(createdOferta, HttpStatus.CREATED);
    }

    @PostMapping("/aplicar")
    @PreAuthorize("hasRole('Student')")
    public ResponseEntity<Void> aplicarAOferta(@RequestBody AplicacionDTO aplicacionDTO){
        ofertaService.aplicarAOferta(aplicacionDTO.getOfertaId(), aplicacionDTO.getUsuarioId(), aplicacionDTO.getCartaPresentacion());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Organization')")
    public ResponseEntity<OfertaResponseDTO> getOfertaById(@PathVariable Long id){
        OfertaResponseDTO oferta = ofertaService.findOfertaById(id);
        return ResponseEntity.ok(oferta);
    }

    @PatchMapping("/desactivar/{id}")
    @PreAuthorize("hasRole('Organization')")
    public ResponseEntity<Void> desactivarOferta(@PathVariable Long id){
        ofertaService.desactivarOferta(id);
        return ResponseEntity.noContent().build();
    }
}
