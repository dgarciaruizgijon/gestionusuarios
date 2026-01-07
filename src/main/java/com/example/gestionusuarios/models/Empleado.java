package com.example.gestionusuarios.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    private String direccion;
    private String puesto;

    @OneToMany(mappedBy = "guia")
    private List<Viaje> viajesGuiados = new ArrayList<>();

    public Empleado() {}

    public Empleado(String nombre, String correo, String direccion, String puesto) {
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
        this.puesto = puesto;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }

    public List<Viaje> getViajesGuiados() { return viajesGuiados; }
    public void setViajesGuiados(List<Viaje> viajesGuiados) { this.viajesGuiados = viajesGuiados; }

    @Override
    public String toString() {
        return nombre + " | " + correo + " | " + direccion + " | " + puesto;
    }
}






// public class Empleado extends Persona implements Ordenable {
//     private String puesto;

//     public Empleado(String nombre, String correo, String direccion, String puesto) throws CorreoInvalidoException {
//         super(nombre, correo, direccion);
//         this.puesto = puesto;
//     }

//     public String getPuesto() {
//         return puesto;
//     }

//     public int compareTo(Persona otro) {
//         return nombre.compareToIgnoreCase(otro.getNombre());
//     }

//     public String toString() {
//         return super.toString() + " | Puesto: " + puesto;
//     }

// }
