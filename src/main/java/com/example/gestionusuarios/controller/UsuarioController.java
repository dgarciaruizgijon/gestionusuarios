package com.example.gestionusuarios.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.gestionusuarios.models.Cliente;
import com.example.gestionusuarios.models.Empleado;
import com.example.gestionusuarios.models.Usuario;
import com.example.gestionusuarios.models.Viaje;
import com.example.gestionusuarios.repositorio.ClienteRepository;
import com.example.gestionusuarios.repositorio.EmpleadoRepository;
import com.example.gestionusuarios.repositorio.UsuarioRepository;
import com.example.gestionusuarios.repositorio.ViajeRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    private final ClienteRepository clienteRepo;
    private final EmpleadoRepository empleadoRepo;
    private final ViajeRepository viajeRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(ClienteRepository clienteRepo, EmpleadoRepository empleadoRepo,
            ViajeRepository viajeRepo, UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        this.clienteRepo = clienteRepo;
        this.empleadoRepo = empleadoRepo;
        this.viajeRepo = viajeRepo;
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    private static final String ULTIMA_VISITA = "ultimaVisita";

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String username, @RequestParam String password, Model model) {
        
        // --- NUEVA VALIDACIÓN DE CONTRASEÑA ---
        // Contamos cuántos caracteres de la contraseña son números
        long cantidadNumeros = password.chars().filter(Character::isDigit).count();
        
        if (cantidadNumeros < 4) {
            model.addAttribute("error", "La contraseña debe contener al menos 4 números por seguridad.");
            return "registro";
        }

        Usuario usuarioExistente = usuarioRepo.findByUsername(username).orElse(null); 
        
        if (usuarioExistente != null) {
            model.addAttribute("error", "Ese nombre de usuario ya está en uso.");
            return "registro";
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setPassword(passwordEncoder.encode(password));

        nuevoUsuario.setRol("USER"); 
        
        usuarioRepo.save(nuevoUsuario);

        model.addAttribute("mensaje", "¡Registro completado! Ya puedes iniciar sesión.");
        return "login"; 
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("nombreAgencia", "Viajes con David");
        model.addAttribute("mensaje", "Bienvenido a la gestión profesional de la agencia de viajes.");
        model.addAttribute("ultimaVisita", LocalDate.now());
        return "inicio";
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

    /**
     * Muestra la lista completa de viajes disponibles en la plataforma.
     * * @param model Interfaz para enviar la lista de viajes a la vista.
     * @return El nombre del archivo HTML de la vista de viajes.
     */
    @GetMapping("/viajes")
    public String viajes(Model model) {

        List<Viaje> viajes = viajeRepo.findAll();
        List<Empleado> empleados = empleadoRepo.findAll();
        model.addAttribute("viajes", viajes);
        model.addAttribute("empleados", empleados);
        return "viajes";
    }

    /**
     * Recibe los datos del formulario de registro y guarda un nuevo cliente.
     * * @param nombre Nombre completo del cliente.
     * @param correo Email de contacto.
     * @param direccion Dirección postal.
     * @param dni Documento de identidad.
     * @param password Contraseña para el acceso.
     * @return Redirecciona a la vista de clientes con un mensaje de éxito.
     */
    @PostMapping("/agregarCliente")
    public String addCliente(@RequestParam String nombre, 
                            @RequestParam String correo,
                            @RequestParam String direccion, 
                            @RequestParam String dni,
                            @RequestParam String password,
                            Model model) {

        String passwordCifrada = passwordEncoder.encode(password);
                    
        Cliente c = new Cliente(nombre, correo, direccion, dni, passwordCifrada);
        clienteRepo.save(c);

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

    /**
     * Registra un nuevo destino en el catálogo de la agencia.
     * * @param destino Nombre de la ciudad o país de destino.
     * @param fecha Fecha programada para el viaje.
     * @param plazas Cantidad total de asientos disponibles.
     * @param guia Nombre del empleado asignado como guía del viaje.
     * @return Redirección a la lista de viajes con mensaje de confirmación.
     */
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

    /**
     * Vincula a un cliente con un viaje específico, creando una reserva.
     * Verifica previamente si hay plazas disponibles y si el cliente existe.
     * * @param cliente Nombre del cliente que realiza la reserva.
     * @param destino Nombre del destino solicitado.
     * @return Redirección a la vista de reservas con el estado de la operación.
     */
    @PostMapping("/reservar")
    public String reservar(@RequestParam String cliente, @RequestParam String destino, Model model, HttpSession session) {

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

    @PostMapping("/eliminarCliente")
    public String eliminarCliente(@RequestParam String nombre, Model model) {
        Cliente c = clienteRepo.findByNombreIgnoreCase(nombre);
        if (c != null) {
            clienteRepo.delete(c);
            model.addAttribute("mensaje", "Cliente eliminado correctamente.");
        } else {
            model.addAttribute("error", "Cliente no encontrado.");
        }

        model.addAttribute("clientes", clienteRepo.findAll());
        return "clientes";
    }

    @PostMapping("/eliminarEmpleado")
    public String eliminarEmpleado(@RequestParam String nombre, Model model) {
        Empleado e = empleadoRepo.findByNombreIgnoreCase(nombre);
        if (e != null) {
            empleadoRepo.delete(e);
            model.addAttribute("mensaje", "Empleado eliminado correctamente.");
        } else {
            model.addAttribute("error", "Empleado no encontrado.");
        }

        model.addAttribute("empleados", empleadoRepo.findAll());
        return "empleados";
    }

    /**
     * Elimina un viaje del sistema basándose en su destino.
     * * @param destino Nombre exacto del destino a eliminar.
     * @return Redirección a la vista principal de viajes.
     */
    @PostMapping("/eliminarViaje")
    public String eliminarViaje(@RequestParam String destino, Model model) {
        Viaje v = viajeRepo.findByDestinoIgnoreCase(destino);
        if (v != null)
            viajeRepo.delete(v);

        model.addAttribute("mensaje", "Viaje eliminado correctamente.");
        model.addAttribute("viajes", viajeRepo.findAll());
        return "viajes";
    }

    @PostMapping("/eliminarReserva")
    public String eliminarReserva(@RequestParam String cliente, @RequestParam String destino, Model model) {
        
        Cliente clienteObj = clienteRepo.findByNombreIgnoreCase(cliente);
        Viaje viajeObj = viajeRepo.findByDestinoIgnoreCase(destino);

        if (clienteObj != null && viajeObj != null) {
            viajeObj.cancelarReserva(clienteObj); 
            viajeRepo.save(viajeObj); 
            model.addAttribute("mensaje", "Reserva cancelada correctamente.");
        } else {
            model.addAttribute("error", "Error al cancelar la reserva.");
        }

        model.addAttribute("clientes", clienteRepo.findAll());
        model.addAttribute("viajes", viajeRepo.findAll());
        return "reservas";
    }

    /**
     * Busca clientes cuyo nombre coincida con el patrón introducido.
     * * @param patron Texto a buscar en la base de datos.
     * @param model Modelo para pasar la lista de resultados a la vista.
     * @return La vista de clientes con la lista filtrada.
     */
    @GetMapping("/buscarClientes")
    public String buscarClientes(@RequestParam String patron, Model model) {

        List<Cliente> resultado = clienteRepo.findByNombreContainingIgnoreCase(patron);

        model.addAttribute("clientes", resultado);

        return "clientes";
    }

}


