package com.merca.back.security.service;

import com.merca.back.security.entity.Usuario;
import com.merca.back.security.repository.UsuarioRepository;
import javax.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    
    public Optional<Usuario> getUserById(int id) {
        return usuarioRepository.findById(id);
    }
    public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
    public Optional<Usuario> getByPassword(String password) {
        return usuarioRepository.findByPassword(password);
    }
    public boolean existsByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
    public boolean existsByPassword(String password) {
        return usuarioRepository.existsByPassword(password);
    }
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}