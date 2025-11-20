package com.ecoandes.backend_ecoandes.services.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecoandes.backend_ecoandes.models.Imagen;
import com.ecoandes.backend_ecoandes.repository.ImagenRepository;
import com.ecoandes.backend_ecoandes.services.CloudinaryService;
import com.ecoandes.backend_ecoandes.services.ImagenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImagenServiceImpl implements ImagenService {

  private final ImagenRepository imagenRepository;

  private final CloudinaryService cloudinaryService;

  @Override
  public Imagen uploadImagen(MultipartFile file) throws IOException {
    Map<?, ?> uploadResult = cloudinaryService.upload(file);
    String imagenUrl = (String) uploadResult.get("url");
    String imagenId = (String) uploadResult.get("public_id");
    Imagen imagen = new Imagen( file.getOriginalFilename(), imagenUrl, imagenId);
    return imagenRepository.save(imagen);
  }

  @Override
  public void deleteImage(Imagen imagen) throws IOException {
    cloudinaryService.delete(imagen.getImagenId());
    imagenRepository.deleteById(imagen.getId());
  }

}
