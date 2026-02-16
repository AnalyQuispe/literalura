package com.alura.literalura.repository;

import java.util.List;

import com.alura.literalura.model.Libro;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByLenguaje(String lenguaje);
    
}
