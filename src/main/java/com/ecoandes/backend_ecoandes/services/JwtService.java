package com.ecoandes.backend_ecoandes.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecoandes.backend_ecoandes.models.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${jwt.secret:defaultSecretKey}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private long jwtExpiration;

  @Value("${jwt.refresh-token.expiration}")
  private long refreshTokenExpiration;

  public String extractUsername(final String token){
    final Claims jwtToken = Jwts.parser()
                  .verifyWith(getSignInKey())
                  .build()
                  .parseSignedClaims(token)
                  .getPayload();
    return jwtToken.getSubject(); 
  }


  public String generateToken(Usuario usuario) {
    return buildToken(usuario,  jwtExpiration);
  }

  public String generateRefreshToken(Usuario usuario){
    return buildToken(usuario,  refreshTokenExpiration);
  }

  public String buildToken(final Usuario user, final long expiration){
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", user.getId());
    claims.put("nombreCompleto",user.getNombreCompleto());
    claims.put("username", user.getUsername());
    claims.put("email", user.getEmail());
    claims.put("celular", user.getCelular());
    claims.put("direccion", user.getDireccion());
    claims.put("fechaRegistro", user.getFechaRegistro().toString());
    claims.put("rol", user.getRol());
    claims.put("estado", user.getEstado());

    return Jwts.builder()
        .id(user.getId().toString())
        .claims(claims)
        .subject(user.getEmail())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey())
        .compact();
  }

  private SecretKey getSignInKey(){
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public boolean isTokenValid(final String token, final Usuario user){
    final String username = extractUsername(token);
    return (username.equals(user.getEmail())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(final String token){
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(final String token){
    final Claims jwtToken = Jwts.parser()
                  .verifyWith(getSignInKey())
                  .build()
                  .parseSignedClaims(token)
                  .getPayload();
    return jwtToken.getExpiration(); 
  }

}
