package com.example.gestionusuarios.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
    

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    private String direccion;
    private String dni;

    @ManyToMany(mappedBy = "clientes")
    private List<Viaje> viajes = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String nombre, String correo, String direccion, String dni) {
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
        this.dni = dni;
    }

    // GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDni() {
        return dni;
    }

    public List<Viaje> getViajes() {
        return viajes;
    }

    @Override
    public String toString() {
        return nombre + " | " + correo + " | " + direccion + " | " + dni;
    }

}






// public class Cliente extends Persona implements Ordenable {

//     private String dni;

//     public Cliente(String nombre, String correo, String direccion, String dni) throws CorreoInvalidoException {
//         super(nombre, correo, direccion);
//         this.dni = dni;
//     }

//     public String getDni() {
//         return dni;
//     }

//     @Override
//     public int compareTo(Persona otro) {
//         return this.nombre.compareToIgnoreCase(otro.nombre);
//     }

//     @Override
//     public String toString() {
//         return super.toString() + " | DNI: " + dni;
//     }

// }
