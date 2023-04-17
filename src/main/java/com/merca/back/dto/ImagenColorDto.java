package com.merca.back.dto;

import com.merca.back.model.Color;
import com.merca.back.model.Ropa;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImagenColorDto {
    @NotBlank
    private String nombre;
    @NotNull
    private Color color;
    @NotNull
    private Ropa ropas;

    public ImagenColorDto() {}

    public ImagenColorDto(String nombre, Color color, Ropa ropas) {
        this.nombre = nombre;
        this.color = color;
        this.ropas = ropas;
    }
}