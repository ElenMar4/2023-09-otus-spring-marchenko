package ru.otus.marchenko.services;

import ru.otus.marchenko.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
}
