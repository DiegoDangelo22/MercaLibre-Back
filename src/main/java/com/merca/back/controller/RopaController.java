package com.merca.back.controller;

import com.merca.back.dto.RopaDto;
import com.merca.back.dto.UpdateOrdenDto;
import com.merca.back.model.Color;
import com.merca.back.model.ImagenColor;
import com.merca.back.model.Ropa;
import com.merca.back.model.Talle;
//import com.merca.back.model.RopaColor;
import com.merca.back.security.controller.Mensaje;
import com.merca.back.service.ColorService;
import com.merca.back.service.ImagenColorService;
//import com.merca.back.service.RopaColorService;
import com.merca.back.service.RopaService;
import com.merca.back.service.TalleService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@CrossOrigin(origins = {"http://localhost:4200","https://mercalibre-365b2.web.app"})
public class RopaController {
    @Autowired
    RopaService ropaService;
    @Autowired
    TalleService talleService;
    @Autowired
    ColorService colorService;
    @Autowired
    ImagenColorService imagenColorService;
    @Autowired
    private EntityManager entityManager;
    
    @GetMapping("/autoincrement")
    public Integer getAutoincrement() {
        Query query = entityManager.createNativeQuery("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'bnfsluep8dytqqnrj8v9' AND TABLE_NAME = 'ropa'");
        return ((Long) query.getSingleResult()).intValue();
    }
    
    @GetMapping("/por-categoria/{id}")
    public ResponseEntity<List<Ropa>> getByCategoria(@PathVariable int id) {
        List<Ropa> ropas = ropaService.findByCategoria(id);
        return new ResponseEntity<>(ropas, HttpStatus.OK);
    }
    
    @GetMapping("/por-color/{id}")
    public ResponseEntity<List<Ropa>> getByColor(@PathVariable int id) {
        Optional<Color> color = colorService.getOne(id);
        List<Ropa> ropas = ropaService.findByColor(color);
        return new ResponseEntity<>(ropas, HttpStatus.OK);
    }
    
    @GetMapping("/por-talle/{id}")
    public ResponseEntity<List<Ropa>> getByTalle(@PathVariable int id) {
        List<Ropa> ropas = ropaService.findByTalle(id);
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
    
    @GetMapping("/por-precio-rango")
    public List<Ropa> searchProductsByPriceRange(
            @RequestParam("minPrice") int minPrice,
            @RequestParam("maxPrice") int maxPrice) {
        return ropaService.findProductsByPriceRange(minPrice, maxPrice);
    }
    
    @GetMapping("/lista")
    public ResponseEntity<Page<Ropa>> list(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        Page<Ropa> ropaPage = ropaService.list(page, size);
        return new ResponseEntity<>(ropaPage, HttpStatus.OK);
    }
    
@PostMapping(value = "/create")
public ResponseEntity<?> create(@RequestBody RopaDto ropaDto) {
    Ropa ropa = new Ropa(ropaDto.getNombre(), ropaDto.getDescripcion(), ropaDto.getPrecio(), ropaDto.getCategoria());
    ropaService.save(ropa);
    
    List<Color> colorList = new ArrayList<>();
    Color color0 = colorService.getOne(ropaDto.getColores().get(0).getId()).orElse(null);
    colorList.add(color0);
    ropa.setColores(colorList);

    List<ImagenColor> imagenesColor = new ArrayList<>();
    for (ImagenColor imagenColorDto : ropaDto.getImagenesColor()) {
        Color color = colorService.getOne(imagenColorDto.getColor().getId()).orElse(null);
        Talle talle = talleService.getOne(imagenColorDto.getTalle().getId()).orElse(null);
        if (color != null && talle != null) {
            ImagenColor imagenColor = new ImagenColor(imagenColorDto.getNombre(), color, ropa, talle, imagenColorDto.getStock());
            imagenesColor.add(imagenColor);
            imagenColorService.save(imagenColor);
        }
    }
    ropa.setImagenesColor(imagenesColor);
    ropa.setOrden(ropaService.obtenerSiguienteOrden());

    ropaService.save(ropa);

    return new ResponseEntity<>(new Mensaje("Ropa guardada correctamente"), HttpStatus.OK);
}

@PutMapping("/update-orden")
public ResponseEntity<?> updateOrden(@RequestBody UpdateOrdenDto request) {
    int draggedOrden = request.getDraggedOrden();
    int targetOrden = request.getTargetOrden();
    List<Ropa> draggedProduct = ropaService.findByOrden(draggedOrden);
    List<Ropa> targetProduct = ropaService.findByOrden(targetOrden);
    
    draggedProduct.get(0).setOrden(targetOrden);
    targetProduct.get(0).setOrden(draggedOrden);
    
    ropaService.save(draggedProduct.get(0));
    ropaService.save(targetProduct.get(0));
    
    return new ResponseEntity<>(new Mensaje("Orden actualizado correctamente"), HttpStatus.OK);
}

@PutMapping("/{id}/imagen-color")
public ResponseEntity<?> agregarColor(@PathVariable("id") int id, @RequestBody ImagenColor imagenColor) {
    Optional<Ropa> optionalRopa = ropaService.getOne(id);
    if (optionalRopa.isPresent()) {
        Ropa ropa = optionalRopa.get();

        // Crear el color y guardarlo en la base de datos
        Color newColor = colorService.getOne(imagenColor.getColor().getId()).orElseThrow();
//        colorService.save(newColor);

        // Crear la imagen asociada al color y guardarla en la base de datos
        ImagenColor newImagenColor = new ImagenColor(imagenColor.getNombre(), newColor, ropa, imagenColor.getTalle(), imagenColor.getStock());
        imagenColorService.save(newImagenColor);

        // Establecer la relación inversa entre el color y las imágenes
//        imagenColor.setColor(color);
//        imagenColorService.save(imagenColor);

        // Actualizar la lista de imágenes y la lista de colores de la ropa
        ropa.getColores().add(newColor);
        ropa.getImagenesColor().add(newImagenColor);
        ropaService.save(ropa);

        return ResponseEntity.ok(ropa);
    } else {
        return ResponseEntity.notFound().build();
    }
}





    @GetMapping("/lista-rci/{id}")
    public ResponseEntity<List<ImagenColor>> lista(@PathVariable("id") int id) {
        List<ImagenColor> list = imagenColorService.getImagenesColorByRopaId(id);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/ropa-color-imagen/{id}")
    public ResponseEntity<ImagenColor> get(@PathVariable("id") int id) {
        ImagenColor imagenColor = imagenColorService.getOne(id).get();
        return new ResponseEntity(imagenColor, HttpStatus.OK);
    }

//    @GetMapping("/detail/{id}")
//    public ResponseEntity<Ropa> getById(@PathVariable("id") int id) {
//        Ropa ropa = ropaService.getOne(id).get();
//        return new ResponseEntity(ropa, HttpStatus.OK);
//    }
    
    @GetMapping("/detail/{id}")
public ResponseEntity<?> detail(@PathVariable int id) {
        Ropa ropa = ropaService.getOne(id).get();

        List<ImagenColor> imagenesColor = ropaService.findImagenesByRopaId(ropa.getId());
        ropa.setImagenesColor(new ArrayList<>(imagenesColor));

        return new ResponseEntity<>(ropa, HttpStatus.OK);
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
        List<ImagenColor> imagenRopa = imagenColorService.getImagenesColorByRopaId(id);
        imagenColorService.delete(imagenRopa);
        ropaService.delete(id);
        return new ResponseEntity(new Mensaje("Ropa eliminada correctamente"), HttpStatus.OK);
    }
}