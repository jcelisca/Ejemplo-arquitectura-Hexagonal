package com.sofka.arqHexagonalBiblioteca.infraestructure.rest.spring.controller;

import com.sofka.arqHexagonalBiblioteca.application.service.BibliotecaService;
import com.sofka.arqHexagonalBiblioteca.domain.Biblioteca;
import com.sofka.arqHexagonalBiblioteca.infraestructure.rest.spring.dto.BibliotecaDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@RestController
public class BibliotecaController {

    private final BibliotecaService service;

    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PostMapping("/bibliotecaReactiva")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BibliotecaDTO> save(@RequestBody BibliotecaDTO bibliotecaDTO){
        return service.guardar(modelMapper().map(bibliotecaDTO, Biblioteca.class))
                .map(biblioteca -> modelMapper().map(biblioteca, BibliotecaDTO.class));
    }

    @GetMapping("/bibliotecaReactiva")
    public Flux<BibliotecaDTO> findAll(){
        return service.findAll()
                .map(biblioteca -> modelMapper().map(biblioteca, BibliotecaDTO.class));
    }

    @GetMapping("/bibliotecaReactiva/{id}/disponibilidad")
    public Mono<String> consultarDisponibilidad(@PathVariable("id") String id){
        return service.consultarDisponibilidad(id);
    }

    @GetMapping("/bibliotecaReactiva/{id}/prestar")
    public Mono<String> prestarRecurso(@PathVariable("id") String id){
        return service.prestarRecurso(id);
    }

    @GetMapping("/bibliotecaReactiva/{categoria}/recomendar")
    public Flux<BibliotecaDTO> recomendacion(@PathVariable("categoria") String categoria){
        return service.recomendacion(categoria)
                .map(biblioteca -> modelMapper().map(biblioteca, BibliotecaDTO.class));
    }

    @GetMapping("/bibliotecaReactiva/{id}/devolver")
    public Mono<String> devolverRecurso(@PathVariable("id") String id){
        return service.devolverRecurso(id);
    }

    @DeleteMapping("/bibliotecaReactiva/{Id}/delete")
    private Mono<Object> delete(@PathVariable("Id") String id) {
        /*return modelMapper().map(service.delete(id)
                .flatMap(biblioteca -> Mono.just(ResponseEntity.ok(biblioteca)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())), (Type) BibliotecaDTO.class);*/
        return service.delete(id)
                .flatMap(biblioteca -> Mono.just(ResponseEntity.ok(biblioteca)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .map(biblioteca1 -> modelMapper().map(biblioteca1, BibliotecaDTO.class));
    }
}
