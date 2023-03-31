package com.merca.back.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String hexadecimal;

    public ColorDto() {}

    public ColorDto(String nombre, String hexadecimal) {
        this.nombre = nombre;
        this.hexadecimal = hexadecimal;
    }
}