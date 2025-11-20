package com.ecoandes.backend_ecoandes.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Nonnull
  private String nombre;

  @Nonnull
  private String descripcion;

  @Nonnull
  private Double precio;

  @Nonnull
  private Integer stock;

  @Nonnull
  private String categoria;

  private Double descuento;

  @OneToOne(cascade ={ CascadeType.ALL, CascadeType.MERGE}, orphanRemoval = true)
  @JoinColumn(name = "imagen_id", referencedColumnName = "id")
  private Imagen imagen;

  @Nonnull
  private String estado;

  public Producto(String nombre, String descripcion, Double precio, Integer stock, String categoria, Double descuento,
      String estado) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.stock = stock;
    this.categoria = categoria;
    this.descuento = descuento;
    this.estado = estado;
  }

  
}
