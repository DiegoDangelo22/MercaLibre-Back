package com.merca.back.repository;

import com.merca.back.model.RopaColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RopaColorRepository extends JpaRepository<RopaColor, Integer> {}