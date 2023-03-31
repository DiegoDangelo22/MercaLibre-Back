package com.merca.back.dto;

import com.merca.back.model.Categoria;
import com.merca.back.model.Color;
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
    private String imagen;
    @NotNull
    private Color color;
    @NotNull
    private int precio;
    @NotNull
    private Categoria categoria;

    public RopaDto() {}

    public RopaDto(String nombre, String descripcion, String imagen, Color color, int precio, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.color = color;
        this.precio = precio;
        this.categoria = categoria;
    }
}