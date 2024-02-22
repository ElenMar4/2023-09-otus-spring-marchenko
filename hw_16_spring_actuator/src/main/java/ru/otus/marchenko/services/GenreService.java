package ru.otus.marchenko.services;

import ru.otus.marchenko.models.dto.genre.GenreCreateDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
    GenreDto findById(long id);
    GenreDto create(GenreCreateDto genreDto);
}