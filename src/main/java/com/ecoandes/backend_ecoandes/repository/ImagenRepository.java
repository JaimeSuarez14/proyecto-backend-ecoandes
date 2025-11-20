package com.ecoandes.backend_ecoandes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoandes.backend_ecoandes.models.Imagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {

}
