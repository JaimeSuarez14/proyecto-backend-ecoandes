package com.ecoandes.backend_ecoandes.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecoandes.backend_ecoandes.models.Consulta;
import com.ecoandes.backend_ecoandes.repository.ConsutaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ConsultaService {
  final private ConsutaRepository consutaRepository;

  public Consulta createConsulta(Consulta consulta) {
    LocalDateTime fecha = LocalDateTime.now(); // Obtiene la fecha y hora actuales
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    consulta.setFechaRegistro(fecha.format(formato));
   return consutaRepository.save(consulta);
  }

  public List<Consulta> getAllConsultas() {
    return consutaRepository.findAll();
  }
}
