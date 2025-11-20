package com.ecoandes.backend_ecoandes.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecoandes.backend_ecoandes.models.Producto;
import com.ecoandes.backend_ecoandes.services.ProductoService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
  private final ProductoService productoService;

   @PostMapping("/create")
   public ResponseEntity<?> createProducto(
    @RequestParam String nombre, 
    @RequestParam String descripcion, 
    @RequestParam Double precio, 
    @RequestParam Integer stock, 
    @RequestParam String categoria, 
    @RequestParam Double descuento, 
    @RequestParam String estado, 
    
    @RequestParam("file") MultipartFile file
    ) throws IOException {
      Producto producto = new Producto(nombre, descripcion, precio, stock, categoria, descuento, estado);
      var product = productoService.createProducto(producto, file);
      return ResponseEntity.ok(product);
   }

    @PostMapping("/update/{id}")
   public ResponseEntity<?> updateProducto(@PathVariable Long id,
      @RequestParam String nombre, 
    @RequestParam String descripcion, 
    @RequestParam Double precio, 
    @RequestParam Integer stock, 
    @RequestParam String categoria, 
    @RequestParam Double descuento, 
    @RequestParam String estado, 
    @RequestParam(value="file", required = false) MultipartFile file) throws IOException {
      
      Producto producto = new Producto(nombre, descripcion, precio, stock, categoria, descuento, estado);
      var pro = productoService.actualizarProducto(id, producto, file);
      return ResponseEntity.ok(pro);
   }
   
   //VAMOS A RETORNAR LA LISTA DE PRODUCTOS
   @GetMapping("/todos")
   public ResponseEntity<List<Producto>> listarProductos() {
      List<Producto> productos = productoService.getAllProductos();
      return ResponseEntity.ok(productos);
   }

   @GetMapping("/{id}")
   public ResponseEntity<Producto> listarProductos(@PathVariable Long id) {
      Producto producto = productoService.getProducto(id);
      return ResponseEntity.ok(producto);
   }


   @PutMapping("/update-image/{id}")
   public ResponseEntity<Producto > actualizarProductoImagen(@PathVariable Long id, @RequestParam(value="file") MultipartFile file) throws IOException{
    Producto productoExistente = productoService.buscarPorId(id);
    Producto productoActualizado = productoService.actualizarImagenProducto(productoExistente, file);

    return ResponseEntity.ok(productoActualizado);
   }



}
