package com.merca.back.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "imagen_color")
public class ImagenColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String nombre;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id")
    private Color color;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ropa_id")
    private Ropa ropa;

    public ImagenColor() {}

    public ImagenColor(String nombre, Color color, Ropa ropa) {
        this.nombre = nombre;
        this.color = color;
        this.ropa = ropa;
    }
}