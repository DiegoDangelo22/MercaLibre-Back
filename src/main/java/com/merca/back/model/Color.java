package com.merca.back.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
    @Fetch(FetchMode.JOIN)
//    @ElementCollection(fetch = FetchType.EAGER)
//    @ManyToMany(mappedBy = "colores", fetch = FetchType.EAGER)
    @ManyToMany(mappedBy = "colores")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Set<Ropa> ropa = new HashSet<>();
    @Fetch(FetchMode.JOIN)
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