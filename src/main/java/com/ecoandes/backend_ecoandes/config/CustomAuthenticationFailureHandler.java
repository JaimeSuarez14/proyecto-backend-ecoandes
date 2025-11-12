package com.ecoandes.backend_ecoandes.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String errorMessage;
        if (exception instanceof BadCredentialsException) {
            errorMessage = "Usuario o contraseña incorrectos.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "La cuenta está deshabilitada.";
        } else {
            errorMessage = "Error de autenticación.";
        }

        Map<String, String> errorResponse = Map.of("error", errorMessage);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}

