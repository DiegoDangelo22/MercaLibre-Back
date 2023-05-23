package com.merca.back.service;

import com.merca.back.model.Categoria;
import com.merca.back.repository.CategoriaRepository;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CategoriaService {
    @Autowired
    CategoriaRepository categoriaRepository;
    
    public List<Categoria> list() {
        return categoriaRepository.findAll();
    }
    
    public Optional<Categoria> getOne(int id) {
        return categoriaRepository.findById(id);
    }
    
    public Optional<Categoria> getByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }
    
    public void save(Categoria categoria) {
        categoriaRepository.save(categoria);
    }
    
    public void delete(int id) {
        categoriaRepository.deleteById(id);
    }
    
    public boolean existsById(int id) {
        return categoriaRepository.existsById(id);
    }
    
    public boolean existsByNombre(String nombre) {
        return categoriaRepository.existsByNombre(nombre);
    }
}