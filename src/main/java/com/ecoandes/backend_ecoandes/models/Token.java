package com.ecoandes.backend_ecoandes.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tokens")
public class Token {
  public enum TokenType{
    BEARER
  }

  @Id
  @GeneratedValue
  public Long id;

  @Column(unique = true, length = 1024)
  public String token;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;

  public boolean revoked;

  public boolean expired;

  @ManyToOne()
  @JoinColumn(name = "user_id")
  public Usuario user;

}
