package com.ecoandes.backend_ecoandes.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecoandes.backend_ecoandes.models.Producto;

public interface ProductoService {

  Producto createProducto(Producto producto, MultipartFile multipartFile) throws IOException;
  Producto getProducto(Long id);
  Producto actualizarProducto(Long id,Producto producto, MultipartFile multipartFile) throws IOException;
  List<Producto> getAllProductos();
  void deleteProducto(Long id);
  Producto buscarPorId(Long id);
  Producto actualizarImagenProducto(Producto producto, MultipartFile multipartFile) throws IOException;
  
}
