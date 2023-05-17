package com.merca.back.dto;

import com.merca.back.model.ImagenColor;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TalleDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String talle;
    @NotNull
    private Set<ImagenColor> imagenesColor;

    public TalleDto() {}

    public TalleDto(String talle, Set<ImagenColor> imagenesColor) {
        this.talle = talle;
        this.imagenesColor = imagenesColor;
    }
}