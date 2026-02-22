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
        if (this.plazas > 0) { // Si queda al menos 1 plaza disponible
            this.clientes.add(cliente); // Añadimos el cliente al viaje
            this.plazas--; // Restamos 1 al número de plazas disponibles
            return true;
        }
        return false; // No quedan plazas
    }

    // Método para cancelar una reserva
    public boolean cancelarReserva(Cliente cliente) {
        if (this.clientes.remove(cliente)) { // Si el cliente estaba en la lista y se borra con éxito
            this.plazas++; // Le devolvemos 1 plaza libre al viaje
            return true;
        }
        return false;
    }

    // ----------------------------------------------------------------------------------------------------

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


