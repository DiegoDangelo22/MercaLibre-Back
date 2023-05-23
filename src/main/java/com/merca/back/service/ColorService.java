package com.merca.back.service;

import com.merca.back.model.Color;
import com.merca.back.repository.ColorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ColorService {
    @Autowired
    ColorRepository colorRepository;
    
    public List<Color> list() {
        return new ArrayList<>(colorRepository.findAll());
    }
    
    public Optional<Color> getOne(int id) {
        return colorRepository.findById(id);
    }
    
    public Optional<Color> getByNombre(String nombre) {
        return colorRepository.findByNombre(nombre);
    }
    
    public void save(Color color) {
        colorRepository.save(color);
    }
    
    public void delete(int id) {
        colorRepository.deleteById(id);
    }
    
    public boolean existsById(int id) {
        return colorRepository.existsById(id);
    }
    
    public boolean existsByNombre(String nombre) {
        return colorRepository.existsByNombre(nombre);
    }
}