package com.ecoandes.backend_ecoandes.dto;

import com.ecoandes.backend_ecoandes.models.Usuario;


public class UsuarioDto {

  private Long id;
  private String nombreCompleto;

  private String username;

  private String email;

  private String celular;

  private String direccion;

  private String fechaRegistro;

  private String rol;

  private String estado;

  public UsuarioDto(Usuario usu) {
    this.id = usu.getId();
    this.nombreCompleto = usu.getNombreCompleto();
    this.username = usu.getUsername();
    this.email = usu.getEmail();
    this.celular = usu.getCelular();
    this.direccion = usu.getDireccion();
    this.fechaRegistro = usu.getFechaRegistro();
    this.rol = usu.getRol().toString();
    this.estado = usu.getEstado();
  }

  public UsuarioDto(Long id, String nombreCompleto, String username, String email, String celular, String direccion,
      String fechaRegistro, String rol, String estado) {
    this.id = id;
    this.nombreCompleto = nombreCompleto;
    this.username = username;
    this.email = email;
    this.celular = celular;
    this.direccion = direccion;
    this.fechaRegistro = fechaRegistro;
    this.rol = rol;
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getCelular() {
    return celular;
  }

  public String getDireccion() {
    return direccion;
  }

  public String getFechaRegistro() {
    return fechaRegistro;
  }

  public String getRol() {
    return rol;
  }

  public String getEstado() {
    return estado;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setCelular(String celular) {
    this.celular = celular;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public void setFechaRegistro(String fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

}
