package com.ecoandes.backend_ecoandes.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecoandes.backend_ecoandes.controllers.LoginRequest;
import com.ecoandes.backend_ecoandes.controllers.TokenResponse;
import com.ecoandes.backend_ecoandes.models.Rol;
import com.ecoandes.backend_ecoandes.models.Token;
import com.ecoandes.backend_ecoandes.models.Usuario;
import com.ecoandes.backend_ecoandes.repository.TokenRepository;
import com.ecoandes.backend_ecoandes.repository.UsuarioRepository;
import com.ecoandes.backend_ecoandes.dto.UsuarioNoCreadoException;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService{
  private final UsuarioRepository usuarioRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public TokenResponse register(Usuario usuario) {
    boolean isEmail = usuarioRepository.existsByEmail(usuario.getEmail().trim());
    boolean isUsername = usuarioRepository.existsByUsername(usuario.getUsername().trim());
    if (isEmail) {
      throw new UsuarioNoCreadoException("El email ya esta en uso");
    }
    if (isUsername) {
      throw new UsuarioNoCreadoException("El username ya esta en uso");
    }

    LocalDateTime fecha = LocalDateTime.now(); // Obtiene la fecha y hora actuales
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    var user = Usuario.builder()
        .username(usuario.getUsername())
        .password(passwordEncoder.encode(usuario.getPassword()))
        .nombreCompleto(usuario.getNombreCompleto())
        .fechaRegistro(fecha.format(formato))
        .rol(Rol.USUARIO)
        .estado("ACTIVO")
        .email(usuario.getEmail())
        .direccion(usuario.getDireccion())
        .celular(usuario.getCelular())
        .build();
    
    if(usuario.getRol() != null) {
      user.setRol(usuario.getRol());
    }
    
    var savedUser = usuarioRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    return new TokenResponse(jwtToken, refreshToken);
  }

  public TokenResponse login(LoginRequest req) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.username(), req.password()));

    var user = usuarioRepository.findByUsername(req.username());
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return new TokenResponse(jwtToken, refreshToken);
  }

  private void saveUserToken(Usuario user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(Token.TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(final Usuario user) {
    final List<Token> validUserTokens = tokenRepository.findAllByUserIdAndExpiredFalseOrRevokedFalse(user.getId());
    if (!validUserTokens.isEmpty()) {
      for (final Token token : validUserTokens) {
        token.setExpired(true);
        token.setRevoked(true);
      }
      tokenRepository.saveAll(validUserTokens);
    }
  }

  public TokenResponse refreshToken(final String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new RuntimeException("Invalid Bearer token");
    }
    final String refreshToken = authHeader.substring(7);
    final String userEmail = jwtService.extractUsername(refreshToken);

    if (userEmail == null) {
      throw new IllegalArgumentException("Invalid Refresh Token");
    }

    final Usuario user = usuarioRepository.findByEmail(userEmail);

    if (!jwtService.isTokenValid(refreshToken, user)) {
      throw new IllegalArgumentException("Invalid Refresh Token");
    }

    final String accessToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, accessToken);
    return new TokenResponse(accessToken, refreshToken);
  }

  
}