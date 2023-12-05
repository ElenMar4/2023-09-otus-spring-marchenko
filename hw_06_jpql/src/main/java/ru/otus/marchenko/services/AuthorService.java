package ru.otus.marchenko.services;

import ru.otus.marchenko.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();
    Optional<Author> findBiId(long id);
    Author insert(String authorName);
    Author update(long id, String authorName);
    void deleteById(long id);

}
