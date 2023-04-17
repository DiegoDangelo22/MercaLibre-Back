package com.merca.back.dto;

import com.merca.back.model.Categoria;
import com.merca.back.model.Color;
import com.merca.back.model.ImagenColor;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RopaDto {
    @NotBlank
    private String nombre;
    @NotBlank
    private String descripcion;
    @NotBlank
    private List<ImagenColor> imagenesColor;
    @NotNull
    private List<Color> colores;
    @NotNull
    private int precio;
    @NotNull
    private Categoria categoria;

    public RopaDto() {}

    public RopaDto(String nombre, String descripcion, List<ImagenColor> imagenesColor, List<Color> colores, int precio, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenesColor = imagenesColor;
        this.colores = colores;
        this.precio = precio;
        this.categoria = categoria;
    }
    
//    public List<Integer> getColorIds() {
//        return colores.stream().map(Color::getId).collect(Collectors.toList());
//    }

}