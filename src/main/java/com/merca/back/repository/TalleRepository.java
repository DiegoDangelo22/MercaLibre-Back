package com.merca.back.repository;

import com.merca.back.model.Ropa;
import com.merca.back.model.Talle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalleRepository extends JpaRepository<Talle, Integer> {

}