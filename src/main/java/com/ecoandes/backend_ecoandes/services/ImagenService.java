package com.ecoandes.backend_ecoandes.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ecoandes.backend_ecoandes.models.Imagen;

public interface ImagenService {

  Imagen uploadImagen(MultipartFile file) throws IOException;
  void deleteImage(Imagen imagen) throws IOException;

}
