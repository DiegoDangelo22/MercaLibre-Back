package com.merca.back.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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
      property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Color color;
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "ropa_id")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
      property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Ropa ropa;
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "talle_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
      property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Talle talle;

    public ImagenColor() {}

    public ImagenColor(String nombre, Color color, Ropa ropa, Talle talle) {
        this.nombre = nombre;
        this.color = color;
        this.ropa = ropa;
        this.talle = talle;
    }
}