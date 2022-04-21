package com.sofka.arqHexagonalBiblioteca.infraestructure.config.spring;

import com.sofka.arqHexagonalBiblioteca.application.repository.BibliotecaRepository;
import com.sofka.arqHexagonalBiblioteca.application.service.BibliotecaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringServiceConfig {

    @Bean
    public BibliotecaService bibliotecaService(BibliotecaRepository repository){
        return new BibliotecaService(repository);
    }
}
