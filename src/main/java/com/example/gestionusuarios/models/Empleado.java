package com.example.gestionusuarios.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Empleado extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String puesto;

    @OneToMany(mappedBy = "guia")
    private List<Viaje> viajesGuiados = new ArrayList<>();

    public Empleado() {}

    public Empleado(String nombre, String correo, String direccion, String puesto) {
        this.nombre = nombre;       // heredado
        this.correo = correo;       // heredado
        this.direccion = direccion; // heredado
        this.puesto = puesto;
    }

    // Getters y Setters
    public Long getId() { 
        return id; 
    }

    public String getPuesto() { 
        return puesto; 
    }

    public void setPuesto(String puesto) { 
        this.puesto = puesto; 
    }

    public List<Viaje> getViajesGuiados() { 
        return viajesGuiados; 
    }

    public void setViajesGuiados(List<Viaje> viajesGuiados) { 
        this.viajesGuiados = viajesGuiados; 
    }

    @Override
    public String toString() {
        return nombre + " | " + correo + " | " + direccion + " | " + puesto;
    }
}
