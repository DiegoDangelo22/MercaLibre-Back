package com.merca.back.repository;

import com.merca.back.model.Color;
import com.merca.back.model.ImagenColor;
import com.merca.back.model.Ropa;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RopaRepository extends JpaRepository<Ropa, Integer> {
    public Optional<Ropa> findByNombre(String nombreRopa);
    public List<Ropa> findByCategoriaId(int categoriaId);
    public List<Ropa> findByColores(Optional<Color> colores);
    public List<Ropa> findByNombreContainingIgnoreCase(String termino);
    public boolean existsByNombre(String nombreRopa);
    
//    @Query("SELECT ic FROM ImagenColor ic JOIN ic.color c JOIN ic.ropa r WHERE r.id = :ropaId AND c.id = :colorId")
//    Set<ImagenColor> findImagenesByRopaIdAndColorId(@Param("ropaId") int ropaId, @Param("colorId") int colorId);
    
    @Query("SELECT ic FROM ImagenColor ic WHERE ic.ropa.id = :ropaId")
    List<ImagenColor> findImagenesByRopaId(@Param("ropaId") int ropaId);
}