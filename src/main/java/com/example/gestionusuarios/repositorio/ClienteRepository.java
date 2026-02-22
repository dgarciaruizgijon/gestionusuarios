package com.example.gestionusuarios.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionusuarios.models.Cliente;



public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByCorreo(String correo);

    // Para buscar clientes por nombre de forma parcial e ignorando mayúsculas/minúsculas
    List<Cliente> findByNombreContainingIgnoreCase(String patron);

    // Para /reservar
    Cliente findByNombreIgnoreCase(String nombre);
}


// public interface ClienteRepository extends JpaRepository<Cliente, Long> {

//     // Para login
//     Cliente findByCorreoAndPassword(String correo, String password);

//     // Buscar cliente por nombre exacto ignorando mayúsculas/minúsculas
//     Cliente findByNombreIgnoreCase(String nombre);

//     // Opcional: búsqueda por nombre que contenga un texto
//     List<Cliente> findByNombreContainingIgnoreCase(String texto);
// }


