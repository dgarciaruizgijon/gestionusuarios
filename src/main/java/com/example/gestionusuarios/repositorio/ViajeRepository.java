package com.example.gestionusuarios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionusuarios.models.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    Viaje findByDestinoIgnoreCase(String destino);
}

