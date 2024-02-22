package ru.otus.marchenko.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.marchenko.models.Tomato;

@Service
@Slf4j
public class RipeningServiceImpl implements RipeningService{
    private static final String[] SIZE = {"small", "medium", "large"};

    @Override
    public Tomato ripen(Tomato tomato) {

        log.info("Start ripening {}", tomato.getName());
        delay();
        tomato.setRipe(true);
        tomato.setSize(SIZE[RandomUtils.nextInt(0, SIZE.length)]);
        log.info("Finish ripening {} / {}", tomato.getName(), tomato.getSize());
        return tomato;
    }

    private static void delay() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
