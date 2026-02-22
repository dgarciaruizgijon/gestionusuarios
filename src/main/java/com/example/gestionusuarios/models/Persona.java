package com.example.gestionusuarios.models;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Persona {
    protected String nombre;
    protected String correo;
    protected String direccion;

    public Persona(String nombre, String correo, String direccion) throws CorreoInvalidoException {
        if (correo == null || !correo.contains("@")) {
            throw new CorreoInvalidoException("Correo no válido " + correo);
        }

        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
    }

    public Persona() {
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return nombre + " (" + correo + ") - Dirección " + direccion;
    }
}
