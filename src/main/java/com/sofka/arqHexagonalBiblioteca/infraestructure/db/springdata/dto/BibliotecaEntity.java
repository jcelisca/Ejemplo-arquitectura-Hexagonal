package com.sofka.arqHexagonalBiblioteca.infraestructure.db.springdata.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "biblioteca")
public class BibliotecaEntity {

    @Id
    private String id;
    private String name;
    private int disponibilidad;
    private LocalDate fechaPrestamo;
    private String estado;
    private String categoria;
}
