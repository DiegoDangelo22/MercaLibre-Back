package com.merca.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Getter
@Setter
@Entity
@Table(name = "color")
public class Color implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String nombre;
    @NotNull
    private String hexadecimal;
    @Fetch(FetchMode.SELECT)
//    @ElementCollection(fetch = FetchType.EAGER)
//    @ManyToMany(mappedBy = "colores", fetch = FetchType.EAGER)
    @ManyToMany(mappedBy = "colores")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Set<Ropa> ropa = new HashSet<>();
    @Fetch(FetchMode.SELECT)
//    @ElementCollection(fetch = FetchType.EAGER)
//    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
//        @JsonIgnoreProperties({"ropas", "color"})
    
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
//      property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
    private Set<ImagenColor> imagenesColor = new HashSet<>();
    

    public Color() {}

    public Color(String nombre, String hexadecimal) {
        this.nombre = nombre;
        this.hexadecimal = hexadecimal;
    }
}