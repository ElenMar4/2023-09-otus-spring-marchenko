package ru.otus.marchenko.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.marchenko.models.Genre;

public interface GenreReactiveRepository extends ReactiveCrudRepository<Genre, String> {}