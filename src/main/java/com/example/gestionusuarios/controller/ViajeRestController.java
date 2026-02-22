package com.example.gestionusuarios.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionusuarios.models.Viaje;

@RestController
@RequestMapping("/api/viajes")
public class ViajeRestController {

    @GetMapping
    public List<Viaje> listarViajesApi() {
        // Esto devuelve la lista de viajes en formato JSON directamente
        List<Viaje> viajes = new ArrayList<>();
        
        return viajes; 
    }
}
