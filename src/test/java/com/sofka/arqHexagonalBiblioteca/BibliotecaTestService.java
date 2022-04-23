package com.sofka.arqHexagonalBiblioteca;

import com.sofka.arqHexagonalBiblioteca.application.repository.BibliotecaRepository;
import com.sofka.arqHexagonalBiblioteca.application.service.BibliotecaService;
import com.sofka.arqHexagonalBiblioteca.domain.Biblioteca;
import com.sofka.arqHexagonalBiblioteca.infraestructure.rest.spring.dto.BibliotecaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.Mockito.when;


@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Biblioteca.class, BibliotecaDTO.class, BibliotecaService.class})
public class BibliotecaTestService {

    @MockBean
    private BibliotecaRepository repository;

    @Autowired
    private BibliotecaService service;

    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        BibliotecaDTO biblioteca = new BibliotecaDTO();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Soledad");
        biblioteca.setEstado("No prestado");
        biblioteca.setDisponibilidad(4);
        biblioteca.setCategoria("Ficcion");
        biblioteca.setFechaPrestamo(LocalDate.now());
        Flux<Biblioteca> list = Flux.just(modelMapper().map(biblioteca, Biblioteca.class));
        Mono<Biblioteca> elemento = Mono.just(modelMapper().map(biblioteca, Biblioteca.class));

        when(repository.findAll()).thenReturn(list);
        when(repository.findByCategoria("Ficcion")).thenReturn(list);
        when(repository.findById("tytyty5")).thenReturn(elemento);
    }

    @Test
    @DisplayName("GET ALL")
    void find() {
        Flux<Biblioteca> lista = service.findAll();
        Assertions.assertEquals("tytyty5", lista.blockFirst().getId());
        Assertions.assertEquals("Soledad", lista.blockFirst().getName());
        Assertions.assertEquals("No prestado", lista.blockFirst().getEstado());
        Assertions.assertEquals(4, lista.blockFirst().getDisponibilidad());
        Assertions.assertEquals("Ficcion", lista.blockFirst().getCategoria());
    }

    @Test
    @DisplayName("consultar disponibilidad")
    void disponibilidad(){
        Mono<String> element = service.consultarDisponibilidad("tytyty5");
        Assertions.assertEquals("Hay disponibles 4 recursos de Soledad", element.block().toString());
    }

}
