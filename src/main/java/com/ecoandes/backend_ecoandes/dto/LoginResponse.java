package com.ecoandes.backend_ecoandes.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String nombreCompleto;
    private String username;
    private String email;
    private String celular;
    private String direccion;
    private String fechaRegistro;
    private String rol;
    private String estado;
    private String jwt;
}



