package com.proyecto.app.service;

import com.proyecto.app.model.Usuario;
import com.proyecto.app.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio requerido por Spring Security para cargar el usuario desde la BD
 * en base al email y proporcionar sus detalles (contraseña, roles, etc.) para la autenticación.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        
        String rol = (usuario.getRol() != null) ? usuario.getRol().toUpperCase() : "CLIENTE";

        return new User(
                usuario.getEmail(),
                usuario.getContraseña(),
                List.of(new SimpleGrantedAuthority("ROLE_" + rol))
        );
    }
}
