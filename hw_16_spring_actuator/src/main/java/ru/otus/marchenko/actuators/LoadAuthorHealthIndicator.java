package ru.otus.marchenko.actuators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.marchenko.services.BookService;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoadAuthorHealthIndicator implements HealthIndicator {

    private final BookService service;

    @Override
    public Health health() {
        try {
            var authors = service.findAll();
            if (authors.isEmpty()) {
                return Health.down()
                        .status(Status.DOWN)
                        .withDetail("message", "Book list loading error")
                        .build();
            } else {
                return Health.up().withDetail("message", "Book data is ready").build();
            }
        } catch (Exception e) {
            log.error("Book list loading error", e);
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "database access error")
                    .withException(e)
                    .build();
        }
    }
}
