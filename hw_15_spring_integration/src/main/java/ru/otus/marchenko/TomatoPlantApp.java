package ru.otus.marchenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.otus.marchenko.services.TomatoGenerateService;

@SpringBootApplication
@IntegrationComponentScan
public class TomatoPlantApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(TomatoPlantApp.class, args);

        TomatoGenerateService service = ctx.getBean(TomatoGenerateService.class);
        service.startGenerateTomatoLoop();
    }
}