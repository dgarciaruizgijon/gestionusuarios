package com.example.gestionusuarios.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.gestionusuarios.models.Cliente;
import com.example.gestionusuarios.models.Empleado;
import com.example.gestionusuarios.models.Viaje;
import com.example.gestionusuarios.repositorio.ClienteRepository;
import com.example.gestionusuarios.repositorio.EmpleadoRepository;
import com.example.gestionusuarios.repositorio.ViajeRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    private final ClienteRepository clienteRepo;
    private final EmpleadoRepository empleadoRepo;
    private final ViajeRepository viajeRepo;

    public UsuarioController(ClienteRepository clienteRepo, EmpleadoRepository empleadoRepo, ViajeRepository viajeRepo) {
        this.clienteRepo = clienteRepo;
        this.empleadoRepo = empleadoRepo;
        this.viajeRepo = viajeRepo;
    }

    private static final String ULTIMA_VISITA = "ultimaVisita";

    @GetMapping("/")
    public String home(Model model, HttpSession session,
                       @CookieValue(value = "preferencia", defaultValue = "sin preferencia") String preferencia,
                       HttpServletResponse response) {

        session.setAttribute(ULTIMA_VISITA, LocalDate.now());
        model.addAttribute("nombreAgencia", "Viajes con David");
        model.addAttribute("mensaje", "Bienvenido a la gestión profesional de la agencia de viajes.");
        model.addAttribute("ultimaVisita", session.getAttribute(ULTIMA_VISITA));
        return "index";
    }

    @GetMapping("/clientes")
    public String clientes(Model model) {
        List<Cliente> clientes = clienteRepo.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes";
    }

    @GetMapping("/empleados")
    public String empleados(Model model) {
        List<Empleado> empleados = empleadoRepo.findAll();
        model.addAttribute("empleados", empleados);
        return "empleados";
    }

    @GetMapping("/viajes")
    public String viajes(Model model) {
        List<Viaje> viajes = viajeRepo.findAll();
        List<Empleado> empleados = empleadoRepo.findAll();
        model.addAttribute("viajes", viajes);
        model.addAttribute("empleados", empleados);
        return "viajes";
    }

    @PostMapping("/agregarCliente")
    public String addCliente(@RequestParam String nombre, @RequestParam String correo,
                             @RequestParam String direccion, @RequestParam String dni,
                             Model model, HttpSession session, HttpServletResponse response) {

        Cliente c = new Cliente(nombre, correo, direccion, dni);
        clienteRepo.save(c);

        session.setAttribute("usuarioLogueado", nombre);
        Cookie cookie = new Cookie("usuarioLogueado", nombre);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        model.addAttribute("mensaje", "Cliente agregado correctamente.");
        model.addAttribute("clientes", clienteRepo.findAll());
        return "clientes";
    }

    @PostMapping("/agregarEmpleado")
    public String addEmpleado(@RequestParam String nombre, @RequestParam String correo,
                              @RequestParam String direccion, @RequestParam String puesto,
                              Model model) {

        Empleado e = new Empleado(nombre, correo, direccion, puesto);
        empleadoRepo.save(e);

        model.addAttribute("mensaje", "Empleado agregado correctamente.");
        model.addAttribute("empleados", empleadoRepo.findAll());
        return "empleados";
    }

    @PostMapping("/agregarViaje")
    public String addViaje(@RequestParam String destino, @RequestParam String fecha,
                           @RequestParam int plazas, @RequestParam String guia, Model model) {

        Empleado empleadoGuia = empleadoRepo.findByNombreIgnoreCase(guia);
        Viaje viaje = new Viaje(destino, fecha, plazas, empleadoGuia);
        viajeRepo.save(viaje);

        model.addAttribute("mensaje", "Viaje agregado correctamente.");
        model.addAttribute("viajes", viajeRepo.findAll());
        model.addAttribute("empleados", empleadoRepo.findAll());
        return "viajes";
    }

    @PostMapping("/reservar")
    public String reservar(@RequestParam String cliente, @RequestParam String destino, Model model) {

        Cliente clienteObj = clienteRepo.findByNombreIgnoreCase(cliente);
        Viaje viajeEncontrado = viajeRepo.findByDestinoIgnoreCase(destino);

        boolean resultado = false;
        if (clienteObj != null && viajeEncontrado != null) {
            resultado = viajeEncontrado.reservarPlaza(clienteObj);
            viajeRepo.save(viajeEncontrado);
        }

        if (resultado) {
            model.addAttribute("mensaje", "Reserva realizada con éxito.");
        } else {
            model.addAttribute("error", "No hay plazas disponibles o cliente no encontrado.");
        }
        model.addAttribute("clientes", clienteRepo.findAll());
        model.addAttribute("viajes", viajeRepo.findAll());
        return "reservas";
    }

    @PostMapping("/eliminarPersona")
    public String eliminarPersona(@RequestParam String nombre, Model model) {
        Cliente c = clienteRepo.findByNombreIgnoreCase(nombre);
        if (c != null) clienteRepo.delete(c);

        Empleado e = empleadoRepo.findByNombreIgnoreCase(nombre);
        if (e != null) empleadoRepo.delete(e);

        model.addAttribute("mensaje", "Persona eliminada correctamente.");
        model.addAttribute("clientes", clienteRepo.findAll());
        model.addAttribute("empleados", empleadoRepo.findAll());
        return "clientes";
    }

    @PostMapping("/eliminarViaje")
    public String eliminarViaje(@RequestParam String destino, Model model) {
        Viaje v = viajeRepo.findByDestinoIgnoreCase(destino);
        if (v != null) viajeRepo.delete(v);

        model.addAttribute("mensaje", "Viaje eliminado correctamente.");
        model.addAttribute("viajes", viajeRepo.findAll());
        return "viajes";
    }
}



























// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.CookieValue;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.example.gestionusuarios.models.Cliente;
// import com.example.gestionusuarios.models.CorreoInvalidoException;
// import com.example.gestionusuarios.models.Empleado;
// import com.example.gestionusuarios.models.Viaje;

// import jakarta.servlet.http.Cookie;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.HttpSession;

// @Controller
// public class UsuarioController {
//     private static final List<Cliente> clientes = new ArrayList<>();
//     private static final List<Empleado> empleados = new ArrayList<>();
//     private static final List<Viaje> viajes = new ArrayList<>();
//     private static final String ULTIMA_VISITA = "ultimaVisita";

//     static {
//         try {
//             Cliente c1 = new Cliente("David", "davidgarcia@gmail.com", "Utrera", "24523767V");
//             clientes.add(c1);
//             Empleado e1 = new Empleado("Ruben", "rubenRG@gmail.com", "Utrera", "Guía");
//             empleados.add(e1);
//         } catch (CorreoInvalidoException e) {
//         }
//     }

//     // Página de inicio
//     @GetMapping("/")
//     public String home(Model model, HttpSession session,
//             @CookieValue(value = "preferencia", defaultValue = "sin preferencia") String preferencia,
//             HttpServletResponse response) {

//         session.setAttribute(ULTIMA_VISITA, LocalDate.now());

//         model.addAttribute("nombreAgencia", "Viajes con David");
//         model.addAttribute("mensaje", "Bienvenido a la gestión profesional de la agencia de viajes.");
//         model.addAttribute("ultimaVisita", session.getAttribute(ULTIMA_VISITA));

//         return "index";
//     }

//     // Páginas de navegación
//     @GetMapping("/clientes")
//     public String clientes(Model model) {
//         model.addAttribute("clientes", clientes);
//         return "clientes";
//     }

//     @GetMapping("/empleados")
//     public String empleados(Model model) {
//         model.addAttribute("empleados", empleados);
//         return "empleados";
//     }

//     @GetMapping("/viajes")
//     public String viajes(Model model) {
//         model.addAttribute("viajes", viajes);
//         model.addAttribute("empleados", empleados);
//         return "viajes";
//     }

//     @GetMapping("/reservas")
//     public String reservas(Model model) {
//         model.addAttribute("clientes", clientes);
//         model.addAttribute("viajes", viajes);
//         return "reservas";
//     }

//     @GetMapping("/contacto")
//     public String contacto() {
//         return "contacto";
//     }

//     // Operaciones
//     @PostMapping("/agregarCliente")
//     public String addCliente(@RequestParam String nombre, @RequestParam String correo,
//             @RequestParam String direccion, @RequestParam String dni,
//             Model model, HttpSession session, HttpServletResponse response) {
//         try {
//             Cliente c = new Cliente(nombre, correo, direccion, dni);
//             clientes.add(c);
//             session.setAttribute("usuarioLogueado", nombre);
//             Cookie cookie = new Cookie("usuarioLogueado", nombre);
//             cookie.setMaxAge(3600);
//             response.addCookie(cookie);
//             model.addAttribute("mensaje", "Cliente agregado correctamente.");
//         } catch (CorreoInvalidoException e) {
//             model.addAttribute("error", e.getMessage());
//         }
//         model.addAttribute("clientes", clientes);
//         return "clientes";
//     }

//     @PostMapping("/agregarEmpleado")
//     public String addEmpleado(@RequestParam String nombre, @RequestParam String correo,
//             @RequestParam String direccion, @RequestParam String puesto,
//             Model model) {
//         try {
//             Empleado e = new Empleado(nombre, correo, direccion, puesto);
//             empleados.add(e);
//             model.addAttribute("mensaje", "Empleado agregado correctamente.");
//         } catch (CorreoInvalidoException e) {
//             model.addAttribute("error", e.getMessage());
//         }
//         model.addAttribute("empleados", empleados);
//         return "empleados";
//     }

//     @PostMapping("/agregarViaje")
//     public String addViaje(@RequestParam String destino, @RequestParam String fecha,
//             @RequestParam int plazas, @RequestParam String guia, Model model) {
//         Empleado empleadoGuia = null;

//         for (Empleado e : empleados) {
//             if (e.getNombre().equalsIgnoreCase(guia)) {
//                 empleadoGuia = e;
//                 break;
//             }
//         }

//         Viaje viaje = new Viaje(destino, fecha, plazas, empleadoGuia);
//         viajes.add(viaje);
//         model.addAttribute("viajes", viajes);
//         model.addAttribute("empleados", empleados);
//         model.addAttribute("mensaje", "Viaje agregado correctamente.");
//         return "viajes";
//     }

//     @PostMapping("/reservar")
//     public String reservar(@RequestParam String cliente, @RequestParam String destino, Model model) {
//         Cliente clienteObj = null;

//     for (Cliente c : clientes) {
//         if (c.getNombre().equalsIgnoreCase(cliente)) {
//             clienteObj = c;
//             break; 
//         }
//     }

//     boolean resultado = false;

//     if (clienteObj != null) {
//         Viaje viajeEncontrado = null;

//         for (Viaje v : viajes) {
//             if (v.getDestino().equalsIgnoreCase(destino)) {
//                 viajeEncontrado = v;
//                 break;
//             }
//     }

//     if (viajeEncontrado != null) {
//         resultado = viajeEncontrado.reservarPlaza(clienteObj);
//     }
// }

//         if (resultado) {
//             model.addAttribute("mensaje", "Reserva realizada con éxito.");
//         } else {
//             model.addAttribute("error", "No hay plazas disponibles o cliente no encontrado.");
//         }
//         model.addAttribute("clientes", clientes);
//         model.addAttribute("viajes", viajes);
//         return "reservas";
//     }

//     @PostMapping("/eliminarPersona")
//     public String eliminarPersona(@RequestParam String nombre, Model model) {
//         clientes.removeIf(c -> c.getNombre().equalsIgnoreCase(nombre));
//         empleados.removeIf(e -> e.getNombre().equalsIgnoreCase(nombre));
//         model.addAttribute("clientes", clientes);
//         model.addAttribute("empleados", empleados);
//         model.addAttribute("mensaje", "Persona eliminada correctamente.");
//         return "clientes";
//     }

//     @PostMapping("/eliminarViaje")
//     public String eliminarViaje(@RequestParam String destino, Model model) {
//         viajes.removeIf(v -> v.getDestino().equalsIgnoreCase(destino));
//         model.addAttribute("viajes", viajes);
//         model.addAttribute("mensaje", "Viaje eliminado correctamente.");
//         return "viajes";
//     }
// }
