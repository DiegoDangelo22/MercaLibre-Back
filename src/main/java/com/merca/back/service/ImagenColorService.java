package com.merca.back.service;

import com.merca.back.model.ImagenColor;
import com.merca.back.repository.ImagenColorRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImagenColorService {
    @Autowired
    ImagenColorRepository imagenColorRepository;
    
    public List<ImagenColor> lista() {
        return imagenColorRepository.findAll();
    }
    
    public List<ImagenColor> getImagenesColorByRopaId(int id) {
        return imagenColorRepository.findByRopaId(id);
    }
    
    public void save(ImagenColor imagenColor) {
        imagenColorRepository.save(imagenColor);
    }
    
    public void saveAll(Set<ImagenColor> imagenColor) {
        imagenColorRepository.saveAll(imagenColor);
    }
    
    public Optional<ImagenColor> getOne(int id) {
        return imagenColorRepository.findById(id);
    }
    
    public void delete(List<ImagenColor> imagenColor) {
        imagenColorRepository.deleteAllInBatch(imagenColor);
    }
    
    public void deleteByColorId(int id) {
        imagenColorRepository.deleteByColorId(id);
    }
}