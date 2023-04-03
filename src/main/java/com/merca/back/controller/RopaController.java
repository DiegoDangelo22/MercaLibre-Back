package com.merca.back.controller;

import com.merca.back.dto.ImagenColorDto;
import com.merca.back.dto.RopaDto;
import com.merca.back.model.Color;
import com.merca.back.model.ImagenColor;
import com.merca.back.model.Ropa;
import com.merca.back.model.RopaColor;
import com.merca.back.security.controller.Mensaje;
import com.merca.back.service.ImagenColorService;
import com.merca.back.service.RopaColorService;
import com.merca.back.service.RopaService;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ropa")
@CrossOrigin(origins = "http://localhost:4200")
public class RopaController {
    @Autowired
    RopaService ropaService;
    @Autowired
    RopaColorService ropaColorService;
    @Autowired
    ImagenColorService imagenColorService;
    @Autowired
    private EntityManager entityManager;
    
    @GetMapping("/autoincrement")
    public Integer getAutoincrement() {
        Query query = entityManager.createNativeQuery("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'merca' AND TABLE_NAME = 'ropa'");
        return ((BigInteger) query.getSingleResult()).intValue();
    }
    
    @GetMapping("/por-categoria/{id}")
    public ResponseEntity<List<Ropa>> getByCategoria(@PathVariable int id) {
        List<Ropa> ropas = ropaService.findByCategoria(id);
        return new ResponseEntity<>(ropas, HttpStatus.OK);
    }
    
    @GetMapping("/por-color/{id}")
    public ResponseEntity<List<Ropa>> getByColor(@PathVariable int id) {
        List<Ropa> ropas = ropaService.findByColor(id);
        return new ResponseEntity<>(ropas, HttpStatus.OK);
    }
    
    @GetMapping("/buscar")
    public List<RopaDto> buscarRopa2(@RequestParam String termino) {
        return ropaService.buscarRopa(termino);
    }

    @PostMapping("/buscar")
    public ResponseEntity<List<RopaDto>> buscarRopa(@RequestBody String termino) {
        List<RopaDto> listaRopaDto = ropaService.buscarRopa(termino);
        return new ResponseEntity<>(listaRopaDto, HttpStatus.OK);
    }
    
    @GetMapping("/lista")
    public ResponseEntity<List<Ropa>> list() {
        List<Ropa> list = ropaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @PostMapping("/create")
public ResponseEntity<?> create(@RequestBody RopaDto ropaDto) {
    Ropa ropa = new Ropa(ropaDto.getNombre(), ropaDto.getDescripcion(), ropaDto.getPrecio(), ropaDto.getCategoria());

    // Guardar la ropa en la base de datos
    ropaService.save(ropa);

    // Obtener los colores asociados a la prenda
    Set<Color> colores = ropaDto.getColores();

    // Crear una instancia de RopaColor por cada color y guardarla en la base de datos
    for (Color color : colores) {
        RopaColor ropaColor = new RopaColor(ropa, color);
        ropaColorService.save(ropaColor);
    }
    
    
    
    // Obtener las imágenes asociadas a la prenda
List<ImagenColorDto> imagenesColorDto = new ArrayList<>();

for (ImagenColor imagenColor : ropaDto.getImagenesColor()) {
    ImagenColorDto imagenColorDto = new ImagenColorDto();
    imagenColorDto.setNombre(imagenColor.getNombre());
    imagenColorDto.setColor(imagenColor.getColor());
    imagenColorDto.setRopa(imagenColor.getRopa());
    imagenesColorDto.add(imagenColorDto);
}


// Crear una instancia de ImagenColor por cada imagen y guardarla en la base de datos
for (ImagenColorDto imagenColorDto : imagenesColorDto) {
    ImagenColor imagenColor = new ImagenColor(imagenColorDto.getNombre(), imagenColorDto.getColor(), ropa);
    imagenColorService.save(imagenColor);
}


    return new ResponseEntity(new Mensaje("Ropa guardada correctamente"), HttpStatus.OK);
    }

    
    @GetMapping("/detail/{id}")
    public ResponseEntity<Ropa> getById(@PathVariable("id") int id) {
        Ropa ropa = ropaService.getOne(id).get();
        return new ResponseEntity(ropa, HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody RopaDto ropaDto) {
        Ropa ropa = ropaService.getOne(id).get();
        ropa.setNombre(ropaDto.getNombre());
        ropa.setDescripcion(ropaDto.getDescripcion());
        ropa.setImagenesColor(ropaDto.getImagenesColor());
        ropa.setCategoria(ropaDto.getCategoria());
        ropa.setColores(ropaDto.getColores());
        ropa.setPrecio(ropaDto.getPrecio());
        ropaService.save(ropa);
        return new ResponseEntity(new Mensaje("Ropa actualizada correctamente"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        ropaService.delete(id);
        return new ResponseEntity(new Mensaje("Ropa eliminada correctamente"), HttpStatus.OK);
    }
}