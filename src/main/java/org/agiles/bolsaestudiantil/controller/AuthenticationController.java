package org.agiles.bolsaestudiantil.controller;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.authentication.LoginRequest;
import org.agiles.bolsaestudiantil.dto.authentication.LoginResponse;
import org.agiles.bolsaestudiantil.dto.authentication.RegisterRequest;
import org.agiles.bolsaestudiantil.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
