package ru.otus.marchenko.repositories;

import ru.otus.marchenko.models.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> findAll();

    List<Genre> findAllByIds(List<Long> ids);
}
