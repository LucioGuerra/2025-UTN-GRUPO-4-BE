package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.CreateAssociationRequestDTO;
import org.agiles.bolsaestudiantil.dto.request.update.OrganizationUpdateRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.LinkedStudentDTO;
import org.agiles.bolsaestudiantil.dto.response.OrganizationResponseDTO;
import org.agiles.bolsaestudiantil.service.AssociationService;
import org.agiles.bolsaestudiantil.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final AssociationService associationService;

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

    @PostMapping("/{organizationId}/linked-students")
    public ResponseEntity<LinkedStudentDTO> linkStudent(@PathVariable Long organizationId, @RequestBody CreateAssociationRequestDTO request){
        LinkedStudentDTO response = associationService.createAssociation(organizationId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{organizationId}/linked-students")
    public ResponseEntity<List<LinkedStudentDTO>> getLinkedStudents(@PathVariable Long organizationId){
        List<LinkedStudentDTO> response = associationService.getAssociationsForOrganization(organizationId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/associations/{associationId}")
    public ResponseEntity<Void> deleteAssociation(@PathVariable Long associationId){
        associationService.deleteAssociation(associationId);
        return ResponseEntity.noContent().build();
    }
}