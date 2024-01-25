package ru.otus.marchenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class WebFlux {
    public static void main(String[] args) {
        SpringApplication.run(WebFlux.class);
    }
}
//старт на http://localhost:8080/book