package ru.otus.marchenko.services;

import ru.otus.marchenko.dto.genre.GenreCreateDto;
import ru.otus.marchenko.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> findAll();
    Optional<Genre> findById(long id);
    void insert(GenreCreateDto genreDto);
}