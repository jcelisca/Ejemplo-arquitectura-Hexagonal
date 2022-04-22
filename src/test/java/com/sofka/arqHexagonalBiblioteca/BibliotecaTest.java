package com.sofka.arqHexagonalBiblioteca;

import com.sofka.arqHexagonalBiblioteca.application.service.BibliotecaService;
import com.sofka.arqHexagonalBiblioteca.domain.Biblioteca;
import com.sofka.arqHexagonalBiblioteca.infraestructure.rest.spring.controller.BibliotecaController;
import com.sofka.arqHexagonalBiblioteca.infraestructure.rest.spring.dto.BibliotecaDTO;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Biblioteca.class, BibliotecaDTO.class, BibliotecaController.class})
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
                .expectBodyList(Biblioteca.class)
                .value(response ->{
                    Assertions.assertEquals("tytyty5", response.get(0).getId());
                    Assertions.assertEquals("Libro", response.get(0).getName());
                    Assertions.assertEquals("No prestado", response.get(0).getEstado());
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
                .expectBody(String.class)
                .value(response->{
                    Assertions.assertEquals("Hay disponibles 2 libros de Libro", response.toString());
                });
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
                .expectBody(String.class)
                .value(response->{
                    Assertions.assertEquals("Se presta el libro Soledad. Quedan: 1", response.toString());
                });

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
                .expectBodyList(Biblioteca.class)
                .value(response->{
                    Assertions.assertEquals("tytyty5",response.get(0).getId());
                    Assertions.assertEquals("Cien años de soledad",response.get(0).getName());
                    Assertions.assertEquals("No prestado",response.get(0).getEstado());
                    Assertions.assertEquals("tyug34",response.get(1).getId());
                    Assertions.assertEquals("El otoño del patriarca",response.get(1).getName());
                    Assertions.assertEquals("Prestado",response.get(1).getEstado());
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
                .expectBody(String.class)
                .value(response->{
                    Assertions.assertEquals("Se devolvio el libro Soledad", response.toString());
                });
    }

}


