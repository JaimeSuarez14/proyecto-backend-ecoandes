package com.ecoandes.backend_ecoandes.dto;

public class UsuarioNoCreadoException extends RuntimeException{
  public UsuarioNoCreadoException(String mensaje) {
        super(mensaje);
    }
}
