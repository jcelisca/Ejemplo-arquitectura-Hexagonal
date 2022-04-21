package com.sofka.arqHexagonalBiblioteca;

import com.sofka.arqHexagonalBiblioteca.application.service.BibliotecaService;
import com.sofka.arqHexagonalBiblioteca.domain.Biblioteca;
import com.sofka.arqHexagonalBiblioteca.infraestructure.rest.spring.controller.BibliotecaController;
import com.sofka.arqHexagonalBiblioteca.infraestructure.rest.spring.dto.BibliotecaDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BibliotecaService.class, Biblioteca.class, BibliotecaController.class, BibliotecaDTO.class})
public class BibliotecaTest {

    @MockBean
    private BibliotecaService service;

    @Autowired
    private WebTestClient webTestClient;

    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Test
    @DisplayName("GET / todos los recursos")
    void findAll() {
        BibliotecaDTO biblioteca = new BibliotecaDTO();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Libro");
        biblioteca.setEstado("No prestado");
        Flux<Biblioteca> list = Flux.just(modelMapper().map(biblioteca, Biblioteca.class));

        when(service.findAll()).thenReturn(list);

        webTestClient.get()
                .uri("/bibliotecaReactiva")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$",hasSize(1))
                .value(response->{
                    jsonPath("$[0].id", is("tytyty"));
                    jsonPath("$[0].name", is("Libro"));
                    jsonPath("$[0].estado", is("No prestado"));
                });
    }

    @Test
    @DisplayName("GET / consultar disponibilidad")
    void consultarDisponibilidad(){
        BibliotecaDTO biblioteca = new BibliotecaDTO();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Libro");
        biblioteca.setEstado("No prestado");
        biblioteca.setDisponibilidad(2);

        when(service.consultarDisponibilidad("tytyty5")).thenReturn(Mono.just("Hay disponibles 2 libros de Libro"));

        webTestClient.get()
                .uri("/bibliotecaReactiva/{Id}/disponibilidad","tytyty5")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals("Hay disponibles 2 libros de Libro");
    }

    @Test
    @DisplayName("GET / prestar recurso")
    void prestarRecurso(){
        BibliotecaDTO biblioteca = new BibliotecaDTO();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Soledad");
        biblioteca.setEstado("No prestado");
        biblioteca.setDisponibilidad(2);

        when(service.prestarRecurso("tytyty5")).thenReturn(Mono.just("Se presta el libro Soledad. Quedan: 1"));

        webTestClient.get()
                .uri("/bibliotecaReactiva/{Id}/prestar","tytyty5")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals("Se presta el libro Soledad. Quedan: 1");

    }

    @Test
    @DisplayName("GET / recomendar")
    void recomendar(){
        BibliotecaDTO biblioteca = new BibliotecaDTO();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Cien años de soledad");
        biblioteca.setEstado("No prestado");
        biblioteca.setCategoria("Novela");

        BibliotecaDTO biblioteca1 = new BibliotecaDTO();
        biblioteca1.setId("tyug34");
        biblioteca1.setName("El otoño del patriarca");
        biblioteca1.setEstado("Prestado");
        biblioteca1.setCategoria("Novela");
        Flux<Biblioteca> list = Flux.just(modelMapper().map(biblioteca, Biblioteca.class), modelMapper().map(biblioteca1, Biblioteca.class));

        when(service.recomendacion("Novela")).thenReturn(list);

        webTestClient.get()
                .uri("/bibliotecaReactiva/{categoria}/recomendar","Novela")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$",hasSize(2))
                .value(response->{
                    jsonPath("$[0].id", is("tytyty5"));
                    jsonPath("$[0].name", is("Cien años de soledad"));
                    jsonPath("$[0].estado", is("No prestado"));
                    jsonPath("$[1].id", is("tyug34"));
                    jsonPath("$[1].name", is("El otoño del patriarca"));
                    jsonPath("$[1].estado", is("Prestado"));
                });
    }

    @Test
    @DisplayName("GET / devolver recurso")
    void devolverRecurso(){
        BibliotecaDTO biblioteca = new BibliotecaDTO();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Soledad");
        biblioteca.setEstado("No prestado");
        biblioteca.setDisponibilidad(2);

        when(service.devolverRecurso("tytyty5")).thenReturn(Mono.just("Se devolvio el libro Soledad"));
        webTestClient.get()
                .uri("/bibliotecaReactiva/{Id}/devolver","tytyty5")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals("Se devolvio el libro Soledad");
    }

}


