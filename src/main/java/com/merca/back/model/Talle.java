package com.merca.back.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Getter
@Setter
@Entity
@Table(name = "talle")
public class Talle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String talle;
    @OneToMany(mappedBy = "talle")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<ImagenColor> imagenesColor;

    public Talle() {}

    public Talle(String talle, Set<ImagenColor> imagenesColor) {
        this.talle = talle;
        this.imagenesColor = imagenesColor;
    }
}