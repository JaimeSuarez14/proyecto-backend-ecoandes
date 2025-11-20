package com.ecoandes.backend_ecoandes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Imagen {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;

  private String imagenUrl;

  private String imagenId;

  public Imagen( String nombre, String imagenUrl, String imagenId) {
    this.nombre = nombre;
    this.imagenUrl = imagenUrl;
    this.imagenId = imagenId;
  }
  
}
