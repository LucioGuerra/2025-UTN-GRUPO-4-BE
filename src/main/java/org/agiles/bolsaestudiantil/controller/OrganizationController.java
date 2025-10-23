package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.update.OrganizationUpdateRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.OrganizationResponseDTO;
import org.agiles.bolsaestudiantil.service.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponseDTO> getOrganizationById(@PathVariable Long id) {
        OrganizationResponseDTO response = organizationService.getOrganizationById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrganizationResponseDTO> updateOrganization(@PathVariable Long id, @RequestBody OrganizationUpdateRequestDTO request) {
        OrganizationResponseDTO response = organizationService.updateOrganization(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }
}