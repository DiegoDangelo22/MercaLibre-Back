package com.merca.back.controller;

import com.merca.back.dto.RopaDto;
import com.merca.back.model.Color;
import com.merca.back.model.ImagenColor;
import com.merca.back.model.Ropa;
import com.merca.back.model.RopaColor;
//import com.merca.back.model.RopaColor;
import com.merca.back.security.controller.Mensaje;
import com.merca.back.service.ColorService;
import com.merca.back.service.ImagenColorService;
import com.merca.back.service.RopaColorService;
//import com.merca.back.service.RopaColorService;
import com.merca.back.service.RopaService;
import com.merca.back.service.TalleService;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    ColorService colorService;
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
    public ResponseEntity<List<Ropa>> list() {
        List<Ropa> list = ropaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
//    @PostMapping("/create")
//public ResponseEntity<?> create(@RequestBody RopaDto ropaDto) {
//    Ropa ropa = new Ropa(ropaDto.getNombre(), ropaDto.getDescripcion(), ropaDto.getPrecio(), ropaDto.getCategoria());
//System.out.println("Nombre: " + ropaDto.getNombre());
//System.out.println("Descripción: " + ropaDto.getDescripcion());
//System.out.println("Precio: " + ropaDto.getPrecio());
//System.out.println("Categoría: " + ropaDto.getCategoria().getId());
////System.out.println("Colores: " + Arrays.toString(ropaDto.getColores().toArray()));
//for (Color color : ropaDto.getColores()) {
//    System.out.println("ID del color: " + color.getId());
//    System.out.println("NOMBRE DEL COLOR" + color.getNombre());
//    System.out.println("HEXA DEL COLOR" + color.getHexadecimal());
//}
//
//
//    // Guardar la ropa en la base de datos
//        ropaService.save(ropa);
//
//    // Obtener los colores asociados a la prenda
//    Set<Color> colores = new HashSet<>();
//    for (Color color : ropaDto.getColores()) {
//        colores.add(colorService.getOne(color.getId()).get());
//    }
//
//    // Crear una instancia de RopaColor por cada color y guardarla en la base de datos
//    for (Color color : colores) {
//        RopaColor ropaColor = new RopaColor(ropa, color);
//        ropaColorService.save(ropaColor);
//    }
//
//// Obtener las imágenes asociadas a cada color de la prenda
//for (Color color : colores) {
//    Optional<Color> optionalColor = ropaDto.getColores().stream().filter(c -> c.equals(color)).findFirst();
//    if (optionalColor.isPresent()) {
//        Color colorDto = optionalColor.get();
//        Set<ImagenColor> imagenesColor = new HashSet<>();
//        for (ImagenColor imagenColor : colorDto.getImagenesColor()) {
//            if (imagenColor.getColor().equals(color)) {
//                imagenesColor.add(new ImagenColor(imagenColor.getNombre(), color));
//            }
//        }
//        color.setImagenesColor(imagenesColor);
//    }
//}
//
//
//
//
//
//    // Guardar la lista de imágenes asociadas a cada color de la prenda en la base de datos
//    for (Color color : colores) {
//        for (ImagenColor imagenColor : color.getImagenesColor()) {
//            imagenColor.setColor(color);
////            imagenColorService.save(imagenColor);
//        }
//    }
//    
//    // Obtener la instancia actualizada de la ropa desde la base de datos
//     ropa = ropaService.getByNombre(ropa.getNombre()).orElseThrow(() -> new RuntimeException("No se pudo encontrar la ropa con el nombre especificado"));
//
//// Iterar sobre los colores y las imágenes asociadas a la prenda
//Ropa finalRopa = ropa;
//for (Color color : colores) {
//    for (ImagenColor imagenColorDto : color.getImagenesColor().stream().filter(i -> i.getRopa() != null && Integer.valueOf(i.getRopa().getId()).equals(finalRopa.getId())).collect(Collectors.toList())) {
//    
//
//// Verificar que la instancia de Color sea válida
//    if (imagenColorDto.getColor() != null && imagenColorDto.getColor().getId() == color.getId()) {
//        // Crear una instancia de ImagenColor y asignar la ropa
//        ImagenColor imagenColor = new ImagenColor(imagenColorDto.getNombre(), imagenColorDto.getColor());
//        imagenColor.setRopa(ropa);
//        
////         Guardar la instancia de ImagenColor en la base de datos
//        imagenColorService.save(imagenColor);
//    }
//}}
//    return new ResponseEntity(new Mensaje("Ropa guardada correctamente"), HttpStatus.OK);
//}
    
    
@PostMapping(value = "/create")
public ResponseEntity<?> create(@RequestBody RopaDto ropaDto) {
    // Crear la instancia de Ropa
    Ropa ropa = new Ropa(ropaDto.getNombre(), ropaDto.getDescripcion(), ropaDto.getPrecio(), ropaDto.getCategoria());

    // Guardar la ropa en la base de datos
ropaService.save(ropa);

// Obtener los colores asociados a la prenda
Set<Color> colores = new HashSet<>();
//for (Color color3 : ropaDto.getColores()) {
//    System.out.println("ID del color: " + color3.getId());
//    System.out.println("NOMBRE DEL COLOR" + color3.getNombre());
//}
//for(ImagenColor imagenColor : ropaDto.getImagenesColor()) {
//    System.out.println(imagenColor.getNombre());
//}
for (Color color : ropaDto.getColores()) {
    colores.add(colorService.getOne(color.getId()).get());
}

List<ImagenColor> imagenesColor = new ArrayList<>();

for (ImagenColor imagenColorDto : ropaDto.getImagenesColor()) {
    // Verificar que la instancia de Color sea válida
    if (imagenColorDto.getColor() != null) {
        // Obtener el color asociado a la imagen
        Color color = colorService.getOne(imagenColorDto.getColor().getId()).orElse(null);
        if (color != null) {
            // Crear una instancia de ImagenColor y asignar la ropa y el color
            ImagenColor imagenColor = new ImagenColor(imagenColorDto.getNombre(), color, ropa, imagenColorDto.getTalle());
            imagenColor.setColor(color);
            imagenColor.setRopa(ropa);
            imagenesColor.add(imagenColor);
            // Guardar la instancia de ImagenColor en la base de datos
            imagenColorService.save(imagenColor);
        }
    }
}

// Asignar el conjunto de imágenes de la prenda y guardar la instancia de Ropa en la base de datos
ropa.setImagenesColor(imagenesColor);
ropaService.save(ropa);


// Crear una instancia de RopaColor por cada color y guardarla en la base de datos
for (Color color : colores) {
    RopaColor ropaColor = new RopaColor(ropa, color);
    ropaColorService.save(ropaColor);
}

return new ResponseEntity(new Mensaje("Ropa guardada correctamente"), HttpStatus.OK);
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
        ImagenColor newImagenColor = new ImagenColor(imagenColor.getNombre(), newColor, ropa, imagenColor.getTalle());
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
public ResponseEntity<Map<String, Object>> detail(@PathVariable int id) {
    Ropa ropa = ropaService.getOne(id).get();
    
    // Obtener imágenes del color de la ropa
//    if (!ropa.getColores().isEmpty()) {
//        Color color = ropa.getColores().iterator().next();
        List<ImagenColor> imagenesColor = ropaService.findImagenesByRopaId(ropa.getId());
        ropa.setImagenesColor(new ArrayList<>(imagenesColor));
//    }
    
//    Map<String, Object> response = new HashMap<>();
//    response.put("ropa", ropa);
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
        List<ImagenColor> imagenRopa = imagenColorService.getImagenesColorByRopaId(id);
        imagenColorService.delete(imagenRopa);
        ropaService.delete(id);
        return new ResponseEntity(new Mensaje("Ropa eliminada correctamente"), HttpStatus.OK);
    }
}