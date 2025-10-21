package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.EmpresaDTO;
import org.agiles.bolsaestudiantil.dto.response.EmpresaSimpleDTO;
import org.agiles.bolsaestudiantil.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> getAll() {
        List<EmpresaDTO> empresas = empresaService.findAll();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> getById(@PathVariable Long id) {
        EmpresaDTO empresa = empresaService.findById(id);
        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> create(@RequestBody EmpresaDTO empresaDTO) {
        EmpresaDTO createdEmpresa = empresaService.create(empresaDTO);
        return new ResponseEntity<>(createdEmpresa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Organization')")
    public ResponseEntity<EmpresaDTO> update(@PathVariable Long id, @RequestBody EmpresaDTO empresaDTO) {
        EmpresaDTO updatedEmpresa = empresaService.update(id, empresaDTO);
        return ResponseEntity.ok(updatedEmpresa);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Organization')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmpresaSimpleDTO>> searchByNombre(@RequestParam String nombre) {
        List<EmpresaSimpleDTO> empresas = empresaService.searchByNombre(nombre);
        return ResponseEntity.ok(empresas);
    }
}
