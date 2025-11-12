package com.ecoandes.backend_ecoandes.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ecoandes.backend_ecoandes.dto.LoginResponse;
import com.ecoandes.backend_ecoandes.models.Usuario;
import com.ecoandes.backend_ecoandes.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Autowired
  private JwtService jwtService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
        Usuario usuario = (Usuario) authentication.getPrincipal(); 

    LoginResponse responseApi = new LoginResponse();
    responseApi.setId(usuario.getId());
    responseApi.setNombreCompleto(usuario.getNombreCompleto());
    responseApi.setUsername(usuario.getUsername());
    responseApi.setEmail(usuario.getEmail());
    responseApi.setCelular(usuario.getCelular());
    responseApi.setDireccion(usuario.getDireccion());
    responseApi.setFechaRegistro(usuario.getFechaRegistro().toString());
    responseApi.setRol(usuario.getRol().toString());
    responseApi.setEstado(usuario.getEstado());
    responseApi.setJwt(jwtService.generateToken(usuario));

    response.setContentType("application/json");
    new ObjectMapper().writeValue(response.getWriter(), responseApi);

  }

}
