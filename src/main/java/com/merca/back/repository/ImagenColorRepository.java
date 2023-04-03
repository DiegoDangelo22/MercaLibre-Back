package com.merca.back.repository;

import com.merca.back.model.ImagenColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenColorRepository extends JpaRepository<ImagenColor, Integer> {}