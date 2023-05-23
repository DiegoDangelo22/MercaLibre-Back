package com.merca.back.controller;

import com.merca.back.dto.CategoriaDto;
import com.merca.back.model.Categoria;
import com.merca.back.model.Ropa;
import com.merca.back.security.controller.Mensaje;
import com.merca.back.service.CategoriaService;
import com.merca.back.service.RopaService;
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
@RequestMapping("/categoria")
@CrossOrigin(origins = {"http://localhost:4200","https://mercalibre-365b2.web.app"})
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    RopaService ropaService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Categoria>> list() {
        List<Categoria> list = categoriaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoriaDto categoriaDto) {
        Categoria categoria = new Categoria(categoriaDto.getNombre());
        categoriaService.save(categoria);
        return new ResponseEntity(new Mensaje("Categoría guardada correctamente"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        List<Ropa> ropaList = ropaService.findByCategoria(id);
        for(Ropa ropa: ropaList) {
            ropaService.delete(ropa.getId());
        }
        categoriaService.delete(id);
        return new ResponseEntity(new Mensaje("Categoría eliminada correctamente"), HttpStatus.OK);
    }
}