package com.example.gestionusuarios.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.gestionusuarios.models.Usuario;
import com.example.gestionusuarios.repositorio.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // 1. Cambiamos el ClienteRepository por el UsuarioRepository
    private final UsuarioRepository usuarioRepo;

    @Autowired
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 2. Buscamos al usuario en la tabla correcta
        Usuario usuario = usuarioRepo.findByUsername(username).orElse(null); 
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado en la base de datos");
        }

        // 3. Convertimos nuestro Usuario al formato que entiende Spring Security
        return new User(
            usuario.getUsername(),
            usuario.getPassword(),
            // Le pasamos el rol que le guardamos en el registro (ej. "USER")
            Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol())) 
        );
    }
}
















// package com.example.gestionusuarios.services;

// import com.example.gestionusuarios.models.Cliente;
// import com.example.gestionusuarios.repositorio.ClienteRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.*;
// import org.springframework.stereotype.Service;
// import java.util.Collections;

// @Service
// public class UserDetailsServiceImpl implements UserDetailsService {

//     private final ClienteRepository clienteRepo;

//     @Autowired
//     public UserDetailsServiceImpl(ClienteRepository clienteRepo) {
//         this.clienteRepo = clienteRepo;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
//         Cliente cliente = clienteRepo.findByCorreo(correo); // ⚠ Debes añadir findByCorreo en ClienteRepository
//         if (cliente == null) {
//             throw new UsernameNotFoundException("Usuario no encontrado");
//         }

//         return new User(
//             cliente.getCorreo(),
//             cliente.getPassword(),
//             Collections.emptyList() // sin roles por ahora
//         );
//     }
// }
