package ru.otus.marchenko.runers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.marchenko.services.TomatoGenerateService;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {
    private final TomatoGenerateService service;
    @Override
    public void run(String... args) throws Exception {
        service.startGenerateTomatoLoop();
    }
}
