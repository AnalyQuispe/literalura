package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DatosAutor (
    @JsonProperty("name") String nombre,
    @JsonProperty("birth_year") Integer fechaNacimiento,
    @JsonProperty("death_year") Integer fechaFallecimiento

){
    
}
