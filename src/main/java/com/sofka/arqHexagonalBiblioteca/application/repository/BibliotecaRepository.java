package com.sofka.arqHexagonalBiblioteca.application.repository;

import com.sofka.arqHexagonalBiblioteca.domain.Biblioteca;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BibliotecaRepository {

    Mono<Biblioteca> save(Biblioteca biblioteca);
    Flux<Biblioteca> findAll();
    Mono<Biblioteca> findById(String Id);
    Flux<Biblioteca> findByCategoria(String categoria);
    Mono<Biblioteca> deleteById(String Id);

}
