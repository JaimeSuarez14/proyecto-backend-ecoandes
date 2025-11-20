package com.ecoandes.backend_ecoandes.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoandes.backend_ecoandes.models.Usuario;
import com.ecoandes.backend_ecoandes.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins="http://localhost:4200")
public class AuthController {

  private final AuthenticationService usuarioService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody Usuario usuario) {

    try {
      final TokenResponse token = usuarioService.register(usuario);
      return ResponseEntity.ok(token);

    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(new ErrorResponse("Error al registrar usuario : " + e.getMessage()));
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> postMethodName(@RequestBody final LoginRequest request) {
    try {
      final TokenResponse token = usuarioService.login(request);
      return ResponseEntity.ok(token);

    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(new ErrorResponse("Credenciales inválidas : " + e.getMessage()));
    }

  }

  @PostMapping("/refresh")
  public ResponseEntity<?> postMethodName(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
    
    try {
      TokenResponse token = usuarioService.refreshToken(authHeader);
    return ResponseEntity.ok(token);

    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(new ErrorResponse("Credenciales inválidas : " + e.getMessage()));
    }
  }

  public static class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }

}
