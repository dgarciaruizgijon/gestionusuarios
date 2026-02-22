package com.example.gestionusuarios.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionusuarios.models.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    Viaje findByDestinoIgnoreCase(String destino);

    List<Viaje> findByDestinoContainingIgnoreCase(String destino);
}

