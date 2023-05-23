package com.merca.back.service;

import com.merca.back.dto.RopaDto;
import com.merca.back.model.Color;
import com.merca.back.model.ImagenColor;
import com.merca.back.model.Ropa;
import com.merca.back.repository.RopaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RopaService {
    @Autowired
    RopaRepository ropaRepository;
   
  public void agregarColor(int id, Color color) {
    Optional<Ropa> optionalRopa = ropaRepository.findById(id);
    if (optionalRopa.isPresent()) {
        Ropa ropa = optionalRopa.get();
        List<Color> colores = ropa.getColores();
        colores.add(color);
        ropa.setColores(colores);
        ropaRepository.save(ropa);
    } else {
        // Manejo del caso en que no se encuentre la ropa con el id dado
    }
}

  public List<ImagenColor> findImagenesByRopaId(int ropaId) {
        return ropaRepository.findImagenesByRopaId(ropaId);
    }

  public List<Ropa> findByTalle(int talleId) {
      return ropaRepository.findByTalle(talleId);
  }
  
  public List<Ropa> findProductsByPriceRange(int minPrice, int maxPrice) {
        return ropaRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    public Page<Ropa> list(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ropaRepository.findAll(pageRequest);
    }
    
    public List<RopaDto> buscarRopa(String termino) {
        List<Ropa> listaRopa = ropaRepository.findByNombreContainingIgnoreCase(termino);
        List<RopaDto> listaRopaDto = new ArrayList<>();
        for(Ropa ropa: listaRopa) {
            listaRopaDto.add(new RopaDto(ropa.getNombre(), ropa.getDescripcion(), ropa.getImagenesColor(), ropa.getColores(), ropa.getPrecio(), ropa.getCategoria()));
        } return listaRopaDto;
    }
    
    public List<Ropa> findByCategoria(int categoriaId) {
        return ropaRepository.findByCategoriaId(categoriaId);
    }
    
    public List<Ropa> findByColor(Optional<Color> colores) {
        return ropaRepository.findByColores(colores);
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