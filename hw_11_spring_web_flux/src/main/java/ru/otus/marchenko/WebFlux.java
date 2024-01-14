package ru.otus.marchenko;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableMongock
@EnableMongoRepositories
@EnableReactiveMongoRepositories
public class WebFlux {
    public static void main(String[] args) {
        SpringApplication.run(WebFlux.class);
    }
}
//старт на http://localhost:8080/book