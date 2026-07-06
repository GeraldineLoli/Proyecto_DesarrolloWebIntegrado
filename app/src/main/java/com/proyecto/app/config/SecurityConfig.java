package com.proyecto.app.config;

import com.proyecto.app.filter.JwtFilter;
import com.proyecto.app.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private JwtFilter jwtFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    // ─── Bean: PasswordEncoder ────────────────────────────────────────────────
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ─── Bean: AuthenticationManager ─────────────────────────────────────────
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ─── Bean: CORS — permite peticiones desde el frontend Angular ────────────
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // ─── Configuración de rutas y seguridad ───────────────────────────────────
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // ── Rutas PÚBLICAS (sin autenticación) ──────────────────────
                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()           // Login y registro con JWT
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()          // Registro
                .requestMatchers(HttpMethod.GET,  "/api/eventos").permitAll()           // Ver todos los eventos
                .requestMatchers(HttpMethod.GET,  "/api/eventos/{id}").permitAll()      // Ver un evento
                .requestMatchers(HttpMethod.GET,  "/api/eventos/proximos").permitAll()  // Ver eventos próximos
                .requestMatchers(HttpMethod.GET,  "/api/eventos/categoria/**").permitAll() // Filtrar por categoría
                .requestMatchers(HttpMethod.GET,  "/api/eventos/buscar/**").permitAll() // Búsquedas JPQL de eventos
                .requestMatchers(HttpMethod.GET,  "/api/zonas/evento/**").permitAll()   // Ver zonas de un evento
                .requestMatchers(HttpMethod.GET,  "/api/zonas/{id}").permitAll()        // Ver una zona
                .requestMatchers(HttpMethod.GET,  "/api/resenas/evento/**").permitAll() // Ver reseñas de evento
                .requestMatchers(HttpMethod.GET,  "/api/resenas/promedio/**").permitAll() // Ver promedio de reseñas
                .requestMatchers(HttpMethod.GET,  "/api/promociones/codigo/**").permitAll() // Buscar cupón por código

                // ── Rutas de USUARIO AUTENTICADO (CLIENTE o ADMIN) ──────────
                .requestMatchers(HttpMethod.GET,  "/api/usuarios/{id}").authenticated()
                .requestMatchers(HttpMethod.PUT,  "/api/usuarios/{id}").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/pedidos").authenticated()
                .requestMatchers(HttpMethod.GET,  "/api/pedidos/{id}").authenticated()
                .requestMatchers(HttpMethod.GET,  "/api/pedidos/usuario/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/pagos/procesar").authenticated()
                .requestMatchers(HttpMethod.GET,  "/api/pagos/{id}").authenticated()
                .requestMatchers(HttpMethod.GET,  "/api/pagos/usuario/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/entradas").authenticated()
                .requestMatchers(HttpMethod.GET,  "/api/entradas/{id}").authenticated()
                .requestMatchers(HttpMethod.GET,  "/api/entradas/usuario/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/resenas").authenticated()
                .requestMatchers(HttpMethod.PUT,  "/api/resenas/{id}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/resenas/{id}").authenticated()

                // ── Rutas EXCLUSIVAS de ADMIN ────────────────────────────────
                .requestMatchers(HttpMethod.POST,   "/api/eventos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/eventos/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/eventos/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,   "/api/zonas").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/zonas/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/zonas/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,    "/api/usuarios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,    "/api/usuarios/buscar/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,    "/api/pagos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,   "/api/pagos/reembolsar/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,    "/api/pedidos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,    "/api/entradas").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,   "/api/promociones").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/promociones/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/promociones/{id}").hasRole("ADMIN")

                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )
            // Agregar el filtro JWT antes del filtro de autenticación de Spring Security
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
