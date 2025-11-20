package com.ecoandes.backend_ecoandes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoandes.backend_ecoandes.dto.ApiResponse;
import com.ecoandes.backend_ecoandes.dto.UsuarioDto;
import com.ecoandes.backend_ecoandes.models.Usuario;
import com.ecoandes.backend_ecoandes.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioControler {

  final private UsuarioService usuarioService;
  // private final Logger logger = LoggerFactory.getLogger(UsuarioControler.class);

  UsuarioControler(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  // @PostMapping
  // public ResponseEntity<ApiResponse> crearNuevoUsuario(@RequestBody Usuario
  // usuario) {
  // try {
  // usuarioService.createUsuario(usuario);
  // return ResponseEntity.ok(new ApiResponse("mensaje", "Usuario creado con
  // éxito"));
  // } catch (UsuarioNoCreadoException e) {
  // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
  // .body(new ApiResponse("error", "Error al crear el usuario: " +
  // e.getMessage()));
  // } catch (Exception e) {
  // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
  // .body(new ApiResponse("error", "Error inesperado: " + e.getMessage()));
  // }
  // }

  // @PostMapping("/login")
  // public ResponseEntity<?> login(@RequestBody ApiRequestLogin request) {
  // String username = request.getUsername();
  // String password = request.getPassword();
  // Usuario usuarioAutenticado = usuarioService.autenticarUsuario(username,
  // password);

  // if (usuarioAutenticado != null) {
  // UsuarioDto usuarioDto = new UsuarioDto(usuarioAutenticado);
  // return ResponseEntity.ok(usuarioDto);
  // } else {
  // return ResponseEntity.status(401).body(new ApiResponse("error", "Error
  // inesperado: "+"Credenciales incorrectas"));
  // }
  // }

  @GetMapping("/{id}")
  public ResponseEntity<?> getMethodName(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(usuarioService.getById(id));
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ApiResponse("error", "Usuario no encontrado: " + e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("error", "Error al obtener el usuario: " + e.getMessage()));
    }
  }

  @GetMapping
  public ResponseEntity<?> mostrarUsuarios() {
    try {
      return ResponseEntity.ok(usuarioService.getALLUsuarios());
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ApiResponse("error", "UsuarioS no encontradoS: " + e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("error", "Error al obtener el usuario: " + e.getMessage()));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
    try {
      UsuarioDto usu = usuarioService.updateUsuario(id, usuario);
      return ResponseEntity.ok(usu);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ApiResponse("error", "Usuario no encontrado: " + e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("error", "Error al actualizar el usuario: " + e.getMessage()));
    }

  }

  @PostMapping("/validar-password/{id}")
  public ResponseEntity<?> validarPassword(@PathVariable Long id, @RequestBody String passwordActual) {

    try {
      boolean esValido = usuarioService.validarPassword(id, passwordActual);
      if (esValido) {
        return ResponseEntity.ok(new ApiResponse("mensaje", "Password válido"));
      } else {
        //esta opcion invalida automaticamente la sesion y lo cierra
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        //     .body(new ApiResponse("error", "Password inválido"));
        return ResponseEntity.ok(new ApiResponse("error", "Password invalido"));
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("error", "Error al validar el password: " + e.getMessage()));
    }
  }

  @PostMapping("/update-password/{id}")
  public ResponseEntity<?> actualizarPassword(@PathVariable Long id, @RequestBody String passwordActual) {

    try {
      String mensaje = usuarioService.actualizarContraseña(id, passwordActual);
      if (mensaje.equals("Contraseña actualizada con exito")) {
        return ResponseEntity.ok(new ApiResponse("mensaje", mensaje));
      } else {
        //esta opcion invalida automaticamente la sesion y lo cierra
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        //     .body(new ApiResponse("error", "Password inválido"));
        return ResponseEntity.ok(new ApiResponse("error", "Password invalido"));
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("error", "Error al validar el password: " + e.getMessage()));
    }
  }

}
