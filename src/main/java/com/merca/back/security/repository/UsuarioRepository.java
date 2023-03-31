package com.merca.back.security.repository;

import com.merca.back.security.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findById(int id);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByPassword(String password);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByPassword(String password);
}