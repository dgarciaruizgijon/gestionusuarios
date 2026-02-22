package com.example.gestionusuarios.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.gestionusuarios.models.Cliente;
import com.example.gestionusuarios.repositorio.ClienteRepository;

@Configuration
public class InitDataConfig {

    @Bean
    public CommandLineRunner initData(ClienteRepository clienteRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            // Comprobamos si la base de datos está vacía para no duplicar el usuario cada vez que arranques
            if (clienteRepo.count() == 0) {
                // Usamos el constructor de tu controlador: nombre, correo, direccion, dni, password encriptada
                Cliente admin = new Cliente(
                        "Administrador", 
                        "admin@admin.com", 
                        "Sede Central", 
                        "00000000A", 
                        passwordEncoder.encode("1234") // ¡Esta es tu contraseña!
                );
                clienteRepo.save(admin);
                System.out.println("✅ Usuario administrador creado con éxito.");
            }
        };
    }
}
