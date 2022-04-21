package com.sofka.arqHexagonalBiblioteca.infraestructure.config.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.sofka.arqHexagonalBiblioteca.infraestructure")
@EntityScan(basePackages = "com.sofka.arqHexagonalBiblioteca.domain")
public class SpringBootService {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootService.class, args);
    }
}
