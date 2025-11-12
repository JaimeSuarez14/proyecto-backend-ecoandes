package com.ecoandes.backend_ecoandes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoandes.backend_ecoandes.models.Usuario;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario , Long>{
  Boolean existsByEmail(String email);
  Boolean existsByUsername(String username);
  Usuario findByUsername(String username);
  Usuario findByEmail(String email);
  boolean existsByEmailAndIdNot(String email, Long id);
  boolean existsByUsernameAndIdNot(String username, Long id);

}
