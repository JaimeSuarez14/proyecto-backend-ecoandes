package com.ecoandes.backend_ecoandes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoandes.backend_ecoandes.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
