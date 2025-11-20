package com.ecoandes.backend_ecoandes.services;

import java.io.File;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

public interface CloudinaryService {

  Map<?, ?> upload(MultipartFile multipartFile) throws IOException;
  Map<?, ?> upload(File file) throws IOException;
  Map<?, ?> delete(String id) throws IOException;
}
