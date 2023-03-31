package com.merca.back.repository;

import com.merca.back.model.Ropa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RopaRepository extends JpaRepository<Ropa, Integer> {
    public Optional<Ropa> findByNombre(String nombreRopa);
    public List<Ropa> findByCategoriaId(int categoriaId);
    public List<Ropa> findByColorId(int colorId);
    public List<Ropa> findByNombreContainingIgnoreCase(String termino);
    public boolean existsByNombre(String nombreRopa);
}