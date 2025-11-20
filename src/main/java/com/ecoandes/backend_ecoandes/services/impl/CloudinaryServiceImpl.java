package com.ecoandes.backend_ecoandes.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ecoandes.backend_ecoandes.services.CloudinaryService;
import java.io.IOException;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

  private Cloudinary cloudinary;

  public CloudinaryServiceImpl() {
    Map<String, String> config = new HashMap<>();
    config.put("cloud_name", "driq1ikid");
    config.put("api_key", "745375553247248");
    config.put("api_secret", "0MilNQNa8XFQDkifayblgvXmrGM");
    this.cloudinary = new Cloudinary(config);
  }

  @Override
  public Map<?, ?> upload(MultipartFile multipartFile) throws IOException {
    File file = convert(multipartFile);
    Map<String, String>result =  cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
    Files.deleteIfExists(file.toPath());
    String url = (String ) result.get("secure_url");
    String publicId = result.get("public_id");
    Map<String , Object> response = new HashMap<>();
    response.put("url", url);
    response.put("public_id", publicId);
    return response;
  }

  @Override
  public Map<?, ?> upload(File file) throws IOException {
    Map<String, String>result =  cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
    Files.deleteIfExists(file.toPath());
    String url = (String ) result.get("secure_url");
    String publicId = result.get("public_id");
    Map<String , Object> response = new HashMap<>();
    response.put("url", url);
    response.put("public_id", publicId);
    return response;
  }

  @Override
  public Map<?, ?> delete(String id) throws IOException {
    Map<String, String> result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    String publicId = result.get("public_id");
    Map<String, Object> response = new HashMap<>();
    response.put("publicId", publicId);
    return response;
  }

  private File convert(MultipartFile multipartFile) throws IOException {
    File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    try (FileOutputStream fo = new FileOutputStream(file);) {
      fo.write(multipartFile.getBytes());
    }
    return file;
  }

  public File convertSecurely(MultipartFile multipartFile) throws IOException {
    // 1. Obtiene el nombre original para extraer la extensión (opcional, pero útil)
    String originalFilename = multipartFile.getOriginalFilename();
    String extension = "";
    if (originalFilename != null && originalFilename.contains(".")) {
        extension = originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    // 2. Crea un archivo temporal usando el método seguro de Java NIO.
    //    'null' para el prefijo permite que el sistema use uno por defecto.
    //    El sufijo ayuda a mantener la extensión del archivo.
    //    Esto coloca el archivo en el directorio temporal seguro del sistema.
    File file = Files.createTempFile(null, extension).toFile();

    // 3. Escribe el contenido del MultipartFile en el nuevo File
    multipartFile.transferTo(file); // Método más simple y eficiente de Spring/Java

    return file;
}
}
