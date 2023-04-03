package com.merca.back.service;

import com.merca.back.model.ImagenColor;
import com.merca.back.repository.ImagenColorRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImagenColorService {
    @Autowired
    ImagenColorRepository imagenColorRepository;
    public void save(ImagenColor imagenColor) {
        imagenColorRepository.save(imagenColor);
    }
}