package com.example.gestionusuarios.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Cliente extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dni;

    private String password; // <--- añadimos la contraseña

    @ManyToMany(mappedBy = "clientes")
    private List<Viaje> viajes = new ArrayList<>();

    public Cliente() {}

    // Constructor actualizado para incluir contraseña
    public Cliente(String nombre, String correo, String direccion, String dni, String password) {
        this.nombre = nombre;       // heredado
        this.correo = correo;       // heredado
        this.direccion = direccion; // heredado
        this.dni = dni;
        this.password = password;   // nuevo
    }

    // GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Viaje> getViajes() {
        return viajes;
    }

    @Override
    public String toString() {
        return nombre + " | " + correo + " | " + direccion + " | " + dni;
    }
}
