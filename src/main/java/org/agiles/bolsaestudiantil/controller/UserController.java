package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.UserResponseDTO;
import org.agiles.bolsaestudiantil.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}