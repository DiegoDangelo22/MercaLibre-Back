package com.merca.back.controller;

import com.merca.back.dto.TalleDto;
import com.merca.back.model.Talle;
import com.merca.back.security.controller.Mensaje;
import com.merca.back.service.TalleService;
import java.util.List;
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
@RequestMapping("/talle")
@CrossOrigin(origins = {"http://localhost:4200","https://mercalibre-365b2.web.app"})
public class TalleController {
    @Autowired
    TalleService talleService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Talle>> list() {
        List<Talle> list = talleService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TalleDto talleDto) {
        Talle talle = new Talle(talleDto.getTalle(), talleDto.getImagenesColor());
        talleService.save(talle);
        return new ResponseEntity(new Mensaje("Talle creado correctamente"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        talleService.delete(id);
        return new ResponseEntity(new Mensaje("Talle eliminado correctamente"), HttpStatus.OK);
    }
}