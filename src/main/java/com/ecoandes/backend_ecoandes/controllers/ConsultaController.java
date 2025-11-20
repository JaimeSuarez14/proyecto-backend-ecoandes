package com.ecoandes.backend_ecoandes.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoandes.backend_ecoandes.dto.ApiResponse;
import com.ecoandes.backend_ecoandes.models.Consulta;
import com.ecoandes.backend_ecoandes.services.ConsultaService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/consulta")
@CrossOrigin(origins = "http://localhost:4200")
public class ConsultaController {
  
  final private ConsultaService consultaService;

  @PostMapping("/create")
  public ResponseEntity<?> createConsulta(@RequestBody Consulta consulta) {
   try {
      consultaService.createConsulta(consulta);;
      return ResponseEntity.ok(new ApiResponse("success", "Consulta registrada exitosamente"));

    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(new ApiResponse("error", "Error al registrar consulta : " + e.getMessage()));
    }
   
    
  }

  @GetMapping()
  public List<Consulta> getAllConsultas() {
    return consultaService.getAllConsultas();
  }
  
}
