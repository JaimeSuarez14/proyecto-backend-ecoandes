package com.ecoandes.backend_ecoandes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoandes.backend_ecoandes.models.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token , Long> {
  List<Token> findAllByUserIdAndExpiredFalseOrRevokedFalse(long id);
  Token findByToken(String token);

}
