package com.merca.back.controller;

import com.merca.back.dto.ColorDto;
import com.merca.back.model.Color;
import com.merca.back.security.controller.Mensaje;
import com.merca.back.service.ColorService;
import com.merca.back.service.ImagenColorService;
import com.merca.back.service.RopaColorService;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/color")
@CrossOrigin(origins = {"http://localhost:4200","https://mercalibre-365b2.web.app"})
public class ColorController {
    @Autowired
    ColorService colorService;
    @Autowired
    RopaColorService ropaColorService;
    @Autowired
    ImagenColorService imagenColorService;
    @Autowired
    private EntityManager entityManager;
    
    @GetMapping("/autoincrement")
    public Integer getAutoincrement() {
        Query query = entityManager.createNativeQuery("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'bnfsluep8dytqqnrj8v9' AND TABLE_NAME = 'color'");
        return ((Long) query.getSingleResult()).intValue();
    }
    
    @GetMapping("/lista")
    public ResponseEntity<List<Color>> list() {
        List<Color> list = colorService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ColorDto colorDto) {
        Color color = new Color(colorDto.getNombre(), colorDto.getHexadecimal());
        colorService.save(color);
        return new ResponseEntity(new Mensaje("Color guardado correctamente"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        ropaColorService.deleteByColorId(id);
        imagenColorService.deleteByColorId(id);
        colorService.delete(id);
        return new ResponseEntity(new Mensaje("Color eliminado correctamente"), HttpStatus.OK);
    }
}