package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.internal.ApplyFilter;
import org.agiles.bolsaestudiantil.dto.request.ApplyRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.ApplyResponseDTO;
import org.agiles.bolsaestudiantil.service.ApplyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/applies")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping
    public ResponseEntity<ApplyResponseDTO> createApply(@RequestBody ApplyRequestDTO request) {
        ApplyResponseDTO response = applyService.createApply(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ApplyResponseDTO>> getAllApplies(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long offerId,
            Pageable pageable) {
        
        ApplyFilter filter = new ApplyFilter();
        filter.setStudentId(studentId);
        filter.setOfferId(offerId);
        
        Page<ApplyResponseDTO> response = applyService.getAllApplies(filter, pageable);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApplyResponseDTO> updateApply(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        ApplyResponseDTO response = applyService.updateApply(id, updates);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApply(@PathVariable Long id) {
        applyService.deleteApply(id);
        return ResponseEntity.noContent().build();
    }
}