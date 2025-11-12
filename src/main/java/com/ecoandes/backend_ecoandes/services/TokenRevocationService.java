package com.ecoandes.backend_ecoandes.services;

import org.springframework.stereotype.Service;

import com.ecoandes.backend_ecoandes.models.Token;
import com.ecoandes.backend_ecoandes.repository.TokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenRevocationService implements TokenService {

  private final TokenRepository tokenRepository;

  @Override
  public void revokeToken(String token) {
    if (token == null || !token.startsWith("Bearer ")) {
      throw new IllegalArgumentException("Token inválido");
    }
    final String jwtToken = token.substring(7);
    final Token foundToken = tokenRepository.findByToken(jwtToken);
    foundToken.setExpired(true);
    foundToken.setRevoked(true);
    tokenRepository.save(foundToken);
  }
}