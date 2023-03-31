package com.merca.back.repository;

import com.merca.back.model.Color;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    public Optional<Color> findByNombre(String nombre);
    public boolean existsByNombre(String nombre);
}