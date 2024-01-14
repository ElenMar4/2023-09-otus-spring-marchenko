package ru.otus.marchenko.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.marchenko.models.Author;

public interface AuthorReactiveRepository extends ReactiveCrudRepository<Author, String> {}
