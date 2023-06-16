package com.merca.back.repository;

import com.merca.back.model.Color;
import com.merca.back.model.RopaColor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RopaColorRepository extends JpaRepository<RopaColor, Integer> {
    public List<RopaColor> findById(int id);
    public List<RopaColor> findByRopaId(int ropaId);
    public List<RopaColor> findByColorId(int colorId);
    public void deleteByColorId(int id);
}