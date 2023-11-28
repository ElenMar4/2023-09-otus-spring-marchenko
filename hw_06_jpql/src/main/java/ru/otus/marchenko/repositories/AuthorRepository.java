package ru.otus.marchenko.repositories;

import ru.otus.marchenko.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();

    Optional<Author> findById(long id);

    Optional<Author> findByName(String name);

    Author save(Author author);

    Author deleteById(long id);
}
