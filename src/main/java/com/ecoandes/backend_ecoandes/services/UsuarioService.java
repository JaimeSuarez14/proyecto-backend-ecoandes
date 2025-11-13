package com.ecoandes.backend_ecoandes.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecoandes.backend_ecoandes.dto.UsuarioDto;
import com.ecoandes.backend_ecoandes.dto.UsuarioNoCreadoException;
import com.ecoandes.backend_ecoandes.models.Rol;
import com.ecoandes.backend_ecoandes.models.Usuario;
import com.ecoandes.backend_ecoandes.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;

  public UsuarioDto updateUsuario(Long id, Usuario usuarioActualizado) {
    Usuario usuarioExistente = usuarioRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    // Validar email duplicado (excluyendo el mismo usuario)
    boolean emailEnUso = usuarioRepository.existsByEmailAndIdNot(usuarioActualizado.getEmail().trim(), id);
    if (emailEnUso) {
      throw new UsuarioNoCreadoException("El email ya está en uso por otro usuario.");
    }

    // Validar username duplicado (excluyendo el mismo usuario)
    boolean usernameEnUso = usuarioRepository.existsByUsernameAndIdNot(usuarioActualizado.getUsername().trim(), id);
    if (usernameEnUso) {
      throw new UsuarioNoCreadoException("El nombre de usuario ya está en uso por otro usuario.");
    }

    String nuevaPassword = usuarioActualizado.getPassword();

    if (esPasswordHasheada(nuevaPassword)) {
        // Si ya está hasheada, verificar si es igual a la actual
        if (!nuevaPassword.equals(usuarioExistente.getPassword())) {
            //usuarioExistente.setPassword(nuevaPassword); // Reemplazar directamente
        }
    } else {
        // Si está en texto plano, verificar si coincide con la actual
        if (!passwordEncoder.matches(nuevaPassword, usuarioExistente.getPassword())) {
            usuarioExistente.setPassword(passwordEncoder.encode(nuevaPassword));
        }
    }


    // Actualizar todos los campos relevantes
    usuarioExistente.setUsername(usuarioActualizado.getUsername().trim());
    usuarioExistente.setEmail(usuarioActualizado.getEmail().trim());
    usuarioExistente.setNombreCompleto(usuarioActualizado.getNombreCompleto());
    usuarioExistente.setCelular(usuarioActualizado.getCelular());
    usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
    usuarioExistente.setRol(usuarioActualizado.getRol());
    usuarioExistente.setEstado(usuarioActualizado.getEstado());
    // ... agrega aquí cualquier otro campo que quieras actualizar

    Usuario usuarioGuardado = usuarioRepository.save(usuarioExistente);
    return new UsuarioDto(usuarioGuardado);
  }

  public String createUsuario(Usuario usuario) {
    boolean isEmail = usuarioRepository.existsByEmail(usuario.getEmail().trim());
    boolean isUsername = usuarioRepository.existsByUsername(usuario.getUsername().trim());
    if (isEmail) {
      throw new UsuarioNoCreadoException("El email ya esta en uso");
    }
    if (isUsername) {
      throw new UsuarioNoCreadoException("El username ya esta en uso");
    }

    Usuario user = new Usuario(usuario);
    LocalDateTime fecha = LocalDateTime.now(); // Obtiene la fecha y hora actuales
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setFechaRegistro(fecha.format(formato));
    user.setRol(Rol.USUARIO);
    user.setEstado("ACTIVO");
    usuarioRepository.save(user);
    return "Creado con exito";
  }

  public UsuarioDto getById(Long id) throws UsernameNotFoundException {
    Usuario usu = usuarioRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    UsuarioDto usuarioDto = new UsuarioDto(usu);
    return usuarioDto;
  }

  public Usuario getByUsername(String username) {
    return usuarioRepository.findByUsername(username);
  }

  public Usuario findByEmail(String email) {
    return usuarioRepository.findByEmail(email);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByUsername(username);
    if (usuario == null) {
      throw new UsernameNotFoundException("Usuario no encontrado");
    }
    return usuario;
  }

  public List<Usuario> getALLUsuarios() {
    return usuarioRepository.findAll();
  }

  public boolean validarPassword(Long id, String passwordActual) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    return passwordEncoder.matches(passwordActual, usuario.getPassword());
  }

  public String actualizarContraseña(Long id, String passwordNueva) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    usuario.setPassword(passwordEncoder.encode(passwordNueva));
    usuarioRepository.save(usuario);
    return "Contraseña actualizada con exito";
  }

  @SuppressWarnings("null")
  public boolean esPasswordHasheada(String password) {
    return password != null &&
        password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$") &&
            password.length() == 60;
  }

}
