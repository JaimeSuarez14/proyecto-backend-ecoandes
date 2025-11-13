package com.ecoandes.backend_ecoandes.services;

import org.springframework.stereotype.Service;

import com.ecoandes.backend_ecoandes.models.Consulta;
import com.ecoandes.backend_ecoandes.repository.ConsutaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ConsultaService {
  final private ConsutaRepository consutaRepository;

  public void createConsulta(Consulta consulta) {
    consutaRepository.save(consulta);
  }
}
