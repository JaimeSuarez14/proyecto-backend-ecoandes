package com.ecoandes.backend_ecoandes.config;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecoandes.backend_ecoandes.services.JwtService;
import com.ecoandes.backend_ecoandes.services.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.ecoandes.backend_ecoandes.repository.TokenRepository;
import com.ecoandes.backend_ecoandes.repository.UsuarioRepository;
import com.ecoandes.backend_ecoandes.models.Token;
import com.ecoandes.backend_ecoandes.models.Usuario;



@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{
  
  private final JwtService  jwtService;
  private final TokenRepository tokeRepository;
  private final UsuarioService usuarioService;
  private final UsuarioRepository usuarioRepository;
  
  @Override
  protected void doFilterInternal( @NonNull HttpServletRequest request, @NonNull HttpServletResponse response,  @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    
    if(request.getServletPath().contains("/auth")){
      filterChain.doFilter(request, response);
      return;
    }

    final String authHeader = request.getHeader( HttpHeaders.AUTHORIZATION);
    if(authHeader == null || !authHeader.startsWith("Bearer ")){
      filterChain.doFilter(request, response);
      return;
    }

    final String jwtToken = authHeader.substring(7);
    final String userEmail = jwtService.extractUsername(jwtToken);

    if(userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null){
      return;
    }

    final Token token = tokeRepository.findByToken(jwtToken);
    if(token == null || token.isExpired() || token.isRevoked()){
      filterChain.doFilter(request, response);
      return;
    }

    final UserDetails userDetails= this.usuarioService.findByEmail(userEmail);
    final Usuario user = usuarioRepository.findByUsername(userDetails.getUsername());

    if(user == null){
      filterChain.doFilter(request, response);
      return;
    }

    final boolean isTokenValid = jwtService.isTokenValid(jwtToken, user);
    if(!isTokenValid){
      return;
    }

    final var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }
  
}
