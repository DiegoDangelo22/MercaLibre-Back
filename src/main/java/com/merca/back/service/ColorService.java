package com.merca.back.service;

import com.merca.back.model.Color;
import com.merca.back.repository.ColorRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ColorService {
    @Autowired
    ColorRepository colorRepository;
    
    public Set<Color> list() {
        return new HashSet<>(colorRepository.findAll());
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