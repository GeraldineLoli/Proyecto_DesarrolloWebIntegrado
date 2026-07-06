package com.proyecto.app.config;

import com.proyecto.app.model.Usuario;
import com.proyecto.app.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByEmail("admin@ticketapp.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setApellido("Sistema");
                admin.setEmail("admin@ticketapp.com");
                admin.setContraseña(passwordEncoder.encode("1234"));
                admin.setDni("00000000");
                admin.setTelefono("999999999");
                admin.setRol("ADMIN");
                
                usuarioRepository.save(admin);
                System.out.println("✅ USUARIO ADMIN CREADO EXITOSAMENTE");
            }
        };
    }
}
