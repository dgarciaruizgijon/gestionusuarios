package com.example.gestionusuarios.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destino;
    private String fecha;
    private int plazas;

    @ManyToOne
    private Empleado guia;

    @ManyToMany
    private List<Cliente> clientes = new ArrayList<>();

    public Viaje() {}

    public Viaje(String destino, String fecha, int plazas, Empleado guia) {
        this.destino = destino;
        this.fecha = fecha;
        this.plazas = plazas;
        this.guia = guia;
    }

    // Método para reservar plaza
    public boolean reservarPlaza(Cliente cliente) {
        if (plazas > clientes.size()) {
            clientes.add(cliente);
            return true;
        }
        return false;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public int getPlazas() { return plazas; }
    public void setPlazas(int plazas) { this.plazas = plazas; }

    public Empleado getGuia() { return guia; }
    public void setGuia(Empleado guia) { this.guia = guia; }

    public List<Cliente> getClientes() { return clientes; }
    public void setClientes(List<Cliente> clientes) { this.clientes = clientes; }
}







// public class Viaje {

//     private String destino;
//     private String fecha;
//     private int plazasTotales;

//     private List<Cliente> reservas = new ArrayList<>();
//     private Empleado guia;

//     public Viaje(String destino, String fecha, int plazasTotales, Empleado guia) {
//         this.destino = destino;
//         this.fecha = fecha;
//         this.plazasTotales = plazasTotales;
//         this.guia = guia;
//     }

//     public int getPlazasLibres() {
//         return plazasTotales - reservas.size();
//     }

//     public boolean reservarPlaza(Cliente cliente) {
//         if (getPlazasLibres() > 0 && !reservas.contains(cliente)) {
//             reservas.add(cliente);
//             return true;
//         } else {
//             return false;
//         }
//     }

//     public String getDestino() {
//         return destino;
//     }

//     public String getFecha() {
//         return fecha;
//     }

//     public int getPlazasTotales() {
//         return plazasTotales;
//     }

//     public List<Cliente> getReservas() {
//         return reservas;
//     }

//     public Empleado getGuia() {
//         return guia;
//     }

//     @Override
//     public String toString() {
//         return "Viaje a " + destino + " (" + fecha + ")\n" +
//                 "Guía: " + (guia != null ? guia.getNombre() : "No asignado") + "\n" +
//                 "Plazas totales: " + plazasTotales + "\n" +
//                 "Plazas libres: " + getPlazasLibres() + "\n" +
//                 "Clientes reservados: " + reservas.size();
//     }

// }
