package ru.otus.marchenko.services;

import ru.otus.marchenko.dto.author.AuthorCreateDto;
import ru.otus.marchenko.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();
    Optional<Author> findBiId(long id);
    void insert(AuthorCreateDto authorCreateDto);
}