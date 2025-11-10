package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.WorkExperienceRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.WorkExperienceResponseDTO;
import org.agiles.bolsaestudiantil.service.WorkExperienceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work-experiences")
@RequiredArgsConstructor
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @PostMapping
    public ResponseEntity<WorkExperienceResponseDTO> create(@RequestBody WorkExperienceRequestDTO request) {
        WorkExperienceResponseDTO response = workExperienceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkExperienceResponseDTO> update(@PathVariable Long id, @RequestBody WorkExperienceRequestDTO request) {
        WorkExperienceResponseDTO response = workExperienceService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workExperienceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
