package com.ecoandes.backend_ecoandes.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consultas")
public class Consulta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne()
  @JoinColumn(name = "id_usuario")
  private Usuario user;

  @Nonnull
  private String asunto;

  @Nonnull
  private String contenido;

  @Nonnull
  private String fechaRegistro;

  @Nonnull
  private String estado;

  @Nonnull
  private String respuesta;

  @Nonnull
  private String fechaRespuesta;
}
