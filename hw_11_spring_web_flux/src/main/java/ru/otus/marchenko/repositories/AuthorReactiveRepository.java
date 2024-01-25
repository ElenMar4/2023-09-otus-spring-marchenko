package ru.otus.marchenko.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.marchenko.models.Author;
@Repository
public interface AuthorReactiveRepository extends ReactiveCrudRepository<Author, String> {}
