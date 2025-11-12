package com.ecoandes.backend_ecoandes.controllers;


public record TokenResponse(
  String accessToken,
  String refreshToken
) {
  
}
