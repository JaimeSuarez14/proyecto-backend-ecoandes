package com.ecoandes.backend_ecoandes.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoandes.backend_ecoandes.models.Consulta;
import com.ecoandes.backend_ecoandes.services.ConsultaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/consulta")
@CrossOrigin(origins = "http://localhost:4200")
public class ConsultaController {
  
  final private ConsultaService consultaService;

  @PostMapping("/create")
  public void createConsulta(@RequestBody Consulta consulta) {
    consultaService.createConsulta(consulta);
  }
}
