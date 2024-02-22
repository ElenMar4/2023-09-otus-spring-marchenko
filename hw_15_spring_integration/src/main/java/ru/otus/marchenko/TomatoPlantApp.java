package ru.otus.marchenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class TomatoPlantApp {
    public static void main(String[] args) {
        SpringApplication.run(TomatoPlantApp.class, args);
    }
}