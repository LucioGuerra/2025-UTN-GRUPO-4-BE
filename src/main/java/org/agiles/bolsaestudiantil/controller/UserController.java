package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.internal.UserFilter;
import org.agiles.bolsaestudiantil.dto.response.UserResponseDTO;
import org.agiles.bolsaestudiantil.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/keycloak/{keycloakId}")
    public ResponseEntity<UserResponseDTO> getUserByKeycloakId(@PathVariable String keycloakId) {
        UserResponseDTO response = userService.getUserByKeycloakId(keycloakId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDTO>> searchUsers(
            @RequestParam String search,
            Pageable pageable) {
        
        UserFilter filter = new UserFilter();
        filter.setSearch(search);
        
        Page<UserResponseDTO> response = userService.searchUsers(filter, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<UserResponseDTO> uploadProfileImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        UserResponseDTO response = userService.uploadProfileImage(id, file);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/delete-image")
    public ResponseEntity<UserResponseDTO> deleteProfileImage(@PathVariable Long id) {
        UserResponseDTO response = userService.deleteProfileImage(id);
        return ResponseEntity.ok(response);
    }
}