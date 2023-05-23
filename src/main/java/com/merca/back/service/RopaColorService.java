package com.merca.back.service;

import com.merca.back.model.RopaColor;
import com.merca.back.repository.RopaColorRepository;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RopaColorService {
    @Autowired
    RopaColorRepository ropaColorRepository;
    public void save(RopaColor ropaColor) {
        ropaColorRepository.save(ropaColor);
    }
    
    public void deleteByColorId(int id) {
        ropaColorRepository.deleteByColorId(id);
    }
    
    public List<RopaColor> findById(int id) {
        return ropaColorRepository.findById(id);
    }
    
    public List<RopaColor> findByRopa(int ropa) {
        return ropaColorRepository.findByRopa(ropa);
    }
    
    public List<RopaColor> findByColor(int color) {
        return ropaColorRepository.findByColor(color);
    }
}