package ru.otus.marchenko.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.marchenko.models.Tomato;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TomatoGenerateServiceImpl implements TomatoGenerateService {

    private final PlantGateway plant;

    public TomatoGenerateServiceImpl(PlantGateway plant) {
        this.plant = plant;
    }

    @Override
    public void startGenerateTomatoLoop() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < 10; i++) {
            int num = i + 1;
            pool.execute(() -> {
                Collection<Tomato> greenTomatoes = generateTomato(num);
                log.info("----------->>> batch {}, new tomatoes: {}", num,
                        greenTomatoes.stream().map(Tomato::getName)
                                .collect(Collectors.joining(",")));
                Collection<Tomato> redTomatoes = plant.process(greenTomatoes);
                log.info("<<<----------- batch {}, ripen tomatoes: {}\n", num, redTomatoes.stream()
                        .map(Tomato::getName)
                        .collect(Collectors.joining(",")));
            });
            delay();
        }
    }

    private Collection<Tomato> generateTomato(int batch) {
        return List.of(new Tomato(String.format("Tomato_%d-1", batch)),
                new Tomato(String.format("Tomato_%d-2", batch)),
                new Tomato(String.format("Tomato_%d-3", batch)));
    }

    private void delay() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
