package com.alura.literalura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaLibros {
    
    private List<DatosLibro> results;

    public List<DatosLibro> getResults() {
        return results;
    }

}
