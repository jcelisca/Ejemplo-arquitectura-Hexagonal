package com.sofka.arqHexagonalBiblioteca.infraestructure.db.springdata.repository;

import com.sofka.arqHexagonalBiblioteca.application.repository.BibliotecaRepository;
import com.sofka.arqHexagonalBiblioteca.domain.Biblioteca;
import com.sofka.arqHexagonalBiblioteca.infraestructure.db.springdata.dto.BibliotecaEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EnableReactiveMongoRepositories(basePackages = "com.sofka.arqHexagonalBiblioteca.infraestructure.db.springdata.repository")
@RequiredArgsConstructor
@Service
public class BibliotecaDTORepository implements BibliotecaRepository {

    private final SpringDataBibliotecaRepository repository;

    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public Mono<Biblioteca> save(Biblioteca biblioteca) {
        return repository.save(modelMapper().map(biblioteca, BibliotecaEntity.class))
                .map(biblioteca1->modelMapper().map(biblioteca1, Biblioteca.class));
    }

    @Override
    public Flux<Biblioteca> findAll() {
        return repository.findAll()
                .map(bibliotecaEntity -> modelMapper().map(bibliotecaEntity, Biblioteca.class));
    }

    @Override
    public Mono<Biblioteca> findById(String id) {
        return repository.findById(id)
                .map(bibliotecaEntity -> modelMapper().map(bibliotecaEntity, Biblioteca.class));
    }

    @Override
    public Flux<Biblioteca> findByCategoria(String categoria) {
        return repository.findByCategoria(categoria)
                .map(bibliotecaEntity -> modelMapper().map(bibliotecaEntity, Biblioteca.class));
    }

    @Override
    public Mono<Biblioteca> deleteById(String id) {
        return repository.deleteById(id)
                .map(biblioteca -> modelMapper().map(biblioteca, Biblioteca.class));
    }
}
