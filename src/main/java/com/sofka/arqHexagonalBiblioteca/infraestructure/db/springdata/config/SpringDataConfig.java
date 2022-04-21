package com.sofka.arqHexagonalBiblioteca.infraestructure.db.springdata.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.sofka.arqHexagonalBiblioteca.infraestructure.db.springdata.repository")
@ConfigurationProperties("spring.datasource")
@NoArgsConstructor
@Getter
@Setter
@EnableMongoAuditing
@EntityScan(basePackages = "com.sofka.arqHexagonalBiblioteca.infraestructure.db.springdata.dto" )
public class SpringDataConfig {
}
