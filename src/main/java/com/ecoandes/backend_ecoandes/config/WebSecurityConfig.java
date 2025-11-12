package com.ecoandes.backend_ecoandes.config;

import java.util.Arrays;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ecoandes.backend_ecoandes.services.TokenRevocationService;
import com.ecoandes.backend_ecoandes.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final TokenRevocationService tokenService;
  private final UsuarioService usuarioService;
  private final JwtAuthFilter jwtAuthfilter;
  private final PasswordEncoder passwordEncoder;

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    return new DaoAuthenticationProvider(usuarioService) {{
      setPasswordEncoder(passwordEncoder);
    }};
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilita CORS
        .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para simplificar el ejemplo
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/auth/**").permitAll() // Permitir acceso a login y registro
            .anyRequest().authenticated() // Requerir autenticación para cualquier otra solicitud
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthfilter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> logout.logoutUrl("/auth/logout")
            .addLogoutHandler((req, res, auth) -> {
              final var authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
              this.tokenService.revokeToken(authHeader);
            })
            .logoutSuccessHandler((req, res, auth) -> SecurityContextHolder.clearContext()));
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
