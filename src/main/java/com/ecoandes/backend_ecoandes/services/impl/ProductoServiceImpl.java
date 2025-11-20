package com.ecoandes.backend_ecoandes.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecoandes.backend_ecoandes.models.Imagen;
import com.ecoandes.backend_ecoandes.models.Producto;
import com.ecoandes.backend_ecoandes.repository.ProductoRepository;
import com.ecoandes.backend_ecoandes.services.ImagenService;
import com.ecoandes.backend_ecoandes.services.ProductoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

  private final ProductoRepository productoRepository;

  private final ImagenService imagenService;

  @Override
  public Producto getProducto(Long id) {
    return productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
  }

  @Override
  public List<Producto> getAllProductos() {
    return productoRepository.findAll();
  }

  @Override
  public void deleteProducto(Long id) {
    productoRepository.deleteById(id);
  }

  @Override
  public Producto createProducto(Producto producto, MultipartFile multipartFile) throws IOException {
    if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
      throw new IllegalArgumentException("El precio del producto debe ser mayor que cero");
    }
    if (multipartFile != null && !multipartFile.isEmpty()) {
      Imagen imagen = imagenService.uploadImagen(multipartFile);
      producto.setImagen(imagen);
    }
    return productoRepository.save(producto);
  }

  @Override
  @Transactional
  public Producto actualizarProducto(Long id, Producto producto, MultipartFile multipartFile) throws IOException {
    Producto productoExistente = productoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    productoExistente.setNombre(producto.getNombre());
    productoExistente.setDescripcion(producto.getDescripcion());
    productoExistente.setPrecio(producto.getPrecio());
    productoExistente.setStock(producto.getStock());
    productoExistente.setCategoria(producto.getCategoria());
    productoExistente.setDescuento(producto.getDescuento());
    productoExistente.setEstado(producto.getEstado());

    Imagen imagenAnterior = productoExistente.getImagen();

    if (multipartFile != null && !multipartFile.isEmpty()) {
      // Rompe la relación y guarda para liberar la FK
      if (imagenAnterior != null) {
        productoExistente.setImagen(null);
        productoRepository.save(productoExistente);
        imagenService.deleteImage(imagenAnterior);
      }
      // Sube y asocia la nueva imagen
      Imagen nueva = imagenService.uploadImagen(multipartFile);
      productoExistente.setImagen(nueva);
    }

    return productoRepository.save(productoExistente);
  }

  @Override
  public Producto buscarPorId(Long id) {
    Producto productoExistente = productoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    return productoExistente;
  }

  @Override
  @Transactional
  public Producto actualizarImagenProducto(Producto productoExistente, MultipartFile multipartFile) throws IOException {

    Imagen imagenAnterior = productoExistente.getImagen();

    if (multipartFile != null && !multipartFile.isEmpty()) {
      if (imagenAnterior != null) {
        productoExistente.setImagen(null);
        productoRepository.save(productoExistente);
        imagenService.deleteImage(imagenAnterior);
      }
      Imagen nueva = imagenService.uploadImagen(multipartFile);
      productoExistente.setImagen(nueva);
    }

    return productoRepository.save(productoExistente);

  }

}
