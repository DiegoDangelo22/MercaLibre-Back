package com.merca.back.repository;

import com.merca.back.model.Color;
import com.merca.back.model.ImagenColor;
import com.merca.back.model.Ropa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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
    @Override
    Page<Ropa> findAll(Pageable pageable);
    
//    @Query("SELECT ic FROM ImagenColor ic JOIN ic.color c JOIN ic.ropa r WHERE r.id = :ropaId AND c.id = :colorId")
//    Set<ImagenColor> findImagenesByRopaIdAndColorId(@Param("ropaId") int ropaId, @Param("colorId") int colorId);
    
    @Query("SELECT ic FROM ImagenColor ic JOIN FETCH ic.ropa r WHERE r.id = :ropaId")
    List<ImagenColor> findImagenesByRopaId(@Param("ropaId") int ropaId);
    
    @Query("SELECT r FROM Ropa r JOIN FETCH r.imagenesColor ic WHERE ic.talle.id = :talleId")
    List<Ropa> findByTalle(@Param("talleId") int talleId);
    
    @Query("SELECT r FROM Ropa r WHERE r.precio >= :minPrice AND r.precio <= :maxPrice")
    List<Ropa> findByPriceBetween(int minPrice, int maxPrice);
}