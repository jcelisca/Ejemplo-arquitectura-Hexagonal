package com.sofka.arqHexagonalBiblioteca.infraestructure.db.springdata.repository;

import com.sofka.arqHexagonalBiblioteca.infraestructure.db.springdata.dto.BibliotecaEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SpringDataBibliotecaRepository extends ReactiveMongoRepository<BibliotecaEntity, String> {
    Flux<BibliotecaEntity> findByCategoria(String categoria);
}
