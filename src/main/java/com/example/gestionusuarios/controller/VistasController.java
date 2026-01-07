package com.example.gestionusuarios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.gestionusuarios.repositorio.ClienteRepository;
import com.example.gestionusuarios.repositorio.ViajeRepository;

@Controller
public class VistasController {

    private final ClienteRepository clienteRepository;
    private final ViajeRepository viajeRepository;

    public VistasController(ClienteRepository clienteRepository,
                            ViajeRepository viajeRepository) {
        this.clienteRepository = clienteRepository;
        this.viajeRepository = viajeRepository;
    }

    @GetMapping("/reservas")
    public String reservas(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("viajes", viajeRepository.findAll());
        return "reservas";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }
}

