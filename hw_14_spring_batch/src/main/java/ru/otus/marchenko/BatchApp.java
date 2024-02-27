package ru.otus.marchenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BatchApp {
    public static void main(String[] args) {
        SpringApplication.run(BatchApp.class, args);
    }
}