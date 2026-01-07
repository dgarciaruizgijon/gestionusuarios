package com.example.gestionusuarios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionusuarios.models.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Empleado findByNombreIgnoreCase(String nombre);
}

