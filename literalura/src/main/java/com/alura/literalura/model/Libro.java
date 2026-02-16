package com.alura.literalura.model;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Lenguaje;
import com.alura.literalura.model.DatosLibro;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;


@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "libro_autor", joinColumns = @JoinColumn(name = "libro_id"),inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autores;

    private String lenguaje;
    private Integer numDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autores = datosLibro.autores()
            .stream()
            .map(d -> new Autor(d.nombre(),d.fechaNacimiento(),d.fechaFallecimiento()))
            .toList();
        this.lenguaje = datosLibro.lenguaje().get(0);
        this.numDescargas = datosLibro.numDescargas();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getNumDescargas() {
        return numDescargas;
    }

    public void setNumDescargas(Integer numDescargas) {
        this.numDescargas = numDescargas;
    }

    public String toString() {
        return "Libro:\n" +
                "- titulo=" + titulo + '\n' +
                "- autores=" + autores +'\n' +
                "- lenguaje=" + lenguaje;
    }



    
}
