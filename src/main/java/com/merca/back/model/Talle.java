package com.merca.back.model;

import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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