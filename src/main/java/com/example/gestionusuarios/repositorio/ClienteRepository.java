package com.example.gestionusuarios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionusuarios.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByNombreIgnoreCase(String nombre);
}

