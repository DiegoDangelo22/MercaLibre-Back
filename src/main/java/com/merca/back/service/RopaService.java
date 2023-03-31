package com.merca.back.service;

import com.merca.back.dto.RopaDto;
import com.merca.back.model.Ropa;
import com.merca.back.repository.RopaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RopaService {
    @Autowired
    RopaRepository ropaRepository;
    
    public List<Ropa> list() {
        return ropaRepository.findAll();
    }
    
    public List<RopaDto> buscarRopa(String termino) {
        List<Ropa> listaRopa = ropaRepository.findByNombreContainingIgnoreCase(termino);
        List<RopaDto> listaRopaDto = new ArrayList<>();
        for(Ropa ropa: listaRopa) {
            listaRopaDto.add(new RopaDto(ropa.getNombre(), ropa.getDescripcion(), ropa.getImagen(), ropa.getColor(), ropa.getPrecio(), ropa.getCategoria()));
        } return listaRopaDto;
    }
    
    public List<Ropa> findByCategoria(int categoriaId) {
        return ropaRepository.findByCategoriaId(categoriaId);
    }
    
    public List<Ropa> findByColor(int colorId) {
        return ropaRepository.findByColorId(colorId);
    }
    
    public Optional<Ropa> getOne(int id) {
        return ropaRepository.findById(id);
    }
    
    public Optional<Ropa> getByNombre(String nombre) {
        return ropaRepository.findByNombre(nombre);
    }
    
    public void save(Ropa ropa) {
        ropaRepository.save(ropa);
    }
    
    public void delete(int id) {
        ropaRepository.deleteById(id);
    }
    
    public boolean existsById(int id) {
        return ropaRepository.existsById(id);
    }
    
    public boolean existsByNombre(String nombre) {
        return ropaRepository.existsByNombre(nombre);
    }
}