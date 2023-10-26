package ru.otus.marchenko.commandlinerunners;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import ru.otus.marchenko.service.TestRunnerService;

@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
    private final TestRunnerService test;

    @Override
    public void run(String... args){
        logger.info("Start testing!!!");
        test.run();
        logger.info("Stop testing!!!");
    }
}
