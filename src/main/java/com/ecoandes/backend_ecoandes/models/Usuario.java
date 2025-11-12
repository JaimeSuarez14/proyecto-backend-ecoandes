package com.ecoandes.backend_ecoandes.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import jakarta.persistence.GenerationType;

@Builder
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Nonnull
  private String nombreCompleto;
  @Nonnull
  @Column(unique = true)
  private String username;
  @Nonnull
  private String password;

  @Nonnull
  @Column(unique = true)
  private String email;

  @Nonnull
  private String celular;

  @Nonnull
  private String direccion;

  @Nonnull
  private String fechaRegistro;

  @Nonnull
  @Enumerated(EnumType.STRING)
  private Rol rol;

  @Nonnull
  private String estado;

  public Usuario() {
  }

  public Usuario(Long id, String nombreCompleto, String username, String password, String email, String celular,
      String direccion, String fechaRegistro, Rol rol, String estado) {
    this.id = id;
    this.nombreCompleto = nombreCompleto;
    this.username = username;
    this.password = password;
    this.email = email;
    this.celular = celular;
    this.direccion = direccion;
    this.fechaRegistro = fechaRegistro;
    this.rol = rol;
    this.estado = estado;
  }

  public Usuario(Usuario usu) {
    this.id = usu.getId();
    this.nombreCompleto = usu.getNombreCompleto();
    this.username = usu.getUsername();
    this.password = usu.getPassword();
    this.email = usu.getEmail();
    this.celular = usu.getCelular();
    this.direccion = usu.getDireccion();
    this.fechaRegistro = usu.getFechaRegistro();
    this.rol = usu.getRol();
    this.estado = usu.getEstado();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(rol.name());
    return List.of(authority);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return "ACTIVO".equals(estado);
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return "ACTIVO".equals(estado);
  }

  // Getters y Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCelular() {
    return celular;
  }

  public void setCelular(String celular) {
    this.celular = celular;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(String fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }
}