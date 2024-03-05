package ru.otus.marchenko.actuators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.marchenko.repositories.BookRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class LibraryEmptyHealthIndicator implements HealthIndicator {

    private final BookRepository repository;

    @Override
    public Health health() {
        try {
            long booksCount = repository.count();
            return booksCount == 0 ?
                    Health.down().status(Status.DOWN).withDetail("message", "Library is empty.").build() :
                    Health.up().status(Status.UP).withDetail("message", "Library ready.").build();
        } catch (Exception e) {
            log.error("book count detection error", e);
            return Health.down().status(Status.DOWN)
                    .withDetail("message", "Library is empty.")
                    .withException(e)
                    .build();
        }
    }
}
