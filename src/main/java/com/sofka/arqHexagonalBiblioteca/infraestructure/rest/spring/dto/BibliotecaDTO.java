package com.sofka.arqHexagonalBiblioteca.infraestructure.rest.spring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BibliotecaDTO {
    private String id;
    private String name;
    private int disponibilidad;
    private LocalDate fechaPrestamo;
    private String estado;
    private String categoria;
}
