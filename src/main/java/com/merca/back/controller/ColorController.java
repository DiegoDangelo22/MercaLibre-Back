package com.merca.back.controller;

import com.merca.back.dto.ColorDto;
import com.merca.back.model.Color;
import com.merca.back.security.controller.Mensaje;
import com.merca.back.service.ColorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/color")
@CrossOrigin(origins = "http://localhost:4200")
public class ColorController {
    @Autowired
    ColorService colorService;
    
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
}