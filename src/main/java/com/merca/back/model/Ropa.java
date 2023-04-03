package com.merca.back.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@Entity
@Table(name = "ropa")
//@JsonIgnoreProperties({"colores"})
public class Ropa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String nombre;
    @NotNull
    private String descripcion;
    @NotNull
    private int precio;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria;
    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.EAGER)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "ropa_color",
        joinColumns = @JoinColumn(name = "ropa_id"),
        inverseJoinColumns = @JoinColumn(name = "color_id"))
    private Set<Color> colores = new HashSet<>();
    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "ropa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenColor> imagenesColor = new ArrayList<>();

    public Ropa() {}

    public Ropa(String nombre, String descripcion, int precio, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }
}