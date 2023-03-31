package com.merca.back.repository;

import com.merca.back.model.Categoria;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    public Optional<Categoria> findByNombre(String nombre);
    public boolean existsByNombre(String nombre);
}