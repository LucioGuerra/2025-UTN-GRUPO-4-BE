package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.PersonalProjectRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.PersonalProjectResponseDTO;
import org.agiles.bolsaestudiantil.service.PersonalProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personal-projects")
@RequiredArgsConstructor
public class PersonalProjectController {
    private final PersonalProjectService personalProjectService;

    @PostMapping
    public ResponseEntity<PersonalProjectResponseDTO> create(@RequestBody PersonalProjectRequestDTO request) {
        PersonalProjectResponseDTO response = personalProjectService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonalProjectResponseDTO> update(@PathVariable Long id, @RequestBody PersonalProjectRequestDTO request) {
        PersonalProjectResponseDTO response = personalProjectService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personalProjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
