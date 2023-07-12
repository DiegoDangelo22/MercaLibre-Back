package com.merca.back.security.controller;

import com.merca.back.security.dto.JwtDto;
import com.merca.back.security.dto.LoginUsuario;
import com.merca.back.security.dto.NuevoUsuario;
import com.merca.back.security.entity.Rol;
import com.merca.back.security.entity.Usuario;
import com.merca.back.security.entity.UsuarioPrincipal;
import com.merca.back.security.enums.RolNombre;
import com.merca.back.security.jwt.JwtProvider;
import com.merca.back.security.service.RolService;
import com.merca.back.security.service.UsuarioService;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200","https://mercalibre-365b2.web.app"})
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RolService rolService;
    @Autowired
    JwtProvider jwtProvider;
    
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos incorrectos"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        Usuario usuario = new Usuario(nuevoUsuario.getNombreUsuario(), passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nuevoUsuario.getNombreUsuario(), nuevoUsuario.getPassword()));
        String token = jwtProvider.generateToken(authentication);
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        int userId = usuarioPrincipal.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtDto(token, usuarioPrincipal.getNombreUsuario(), userId, usuarioPrincipal.getAuthorities()));
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos obligatorios"), HttpStatus.BAD_REQUEST);
        if(!usuarioService.existsByNombreUsuario(loginUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("Ese usuario no existe"), HttpStatus.BAD_REQUEST);
        Usuario usuarios = usuarioService.getByNombreUsuario(loginUsuario.getNombreUsuario());
        if(!passwordEncoder.matches(loginUsuario.getPassword(), usuarios.getPassword()))
            return new ResponseEntity(new Mensaje("Contraseña incorrecta"), HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        int userId = usuarioPrincipal.getId();
        JwtDto jwtDto = new JwtDto(token, usuarioPrincipal.getNombreUsuario(), userId, usuarioPrincipal.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}