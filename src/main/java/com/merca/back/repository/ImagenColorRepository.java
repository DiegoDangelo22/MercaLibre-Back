package com.merca.back.repository;

import com.merca.back.model.ImagenColor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenColorRepository extends JpaRepository<ImagenColor, Integer> {
    List<ImagenColor> findByRopaId(int id);
    List<ImagenColor> findByColorId(int id);
    public void deleteByColorId(int id);
}