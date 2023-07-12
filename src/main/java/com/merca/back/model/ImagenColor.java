package com.merca.back.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Getter
@Setter
@Entity
@Table(name = "imagen_color")
public class ImagenColor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String nombre;
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "color_id")
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
      property = "id", scope = Color.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Color color;
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "ropa_id")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
      property = "id", scope = Ropa.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Ropa ropa;
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "talle_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
      property = "id", scope = Talle.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Talle talle;
    @NotNull
    private int stock;

    public ImagenColor() {}

    public ImagenColor(String nombre, Color color, Ropa ropa, Talle talle, int stock) {
        this.nombre = nombre;
        this.color = color;
        this.ropa = ropa;
        this.talle = talle;
        this.stock = stock;
    }
}