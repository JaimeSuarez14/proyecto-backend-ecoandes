package com.ecoandes.backend_ecoandes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoandes.backend_ecoandes.models.Usuario;
import com.ecoandes.backend_ecoandes.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody Usuario request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody String token) {
        return ResponseEntity.ok(authenticationService.refreshToken(token));
    }
}