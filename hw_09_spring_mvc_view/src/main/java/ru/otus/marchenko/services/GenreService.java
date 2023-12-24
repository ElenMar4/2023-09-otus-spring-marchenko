package ru.otus.marchenko.services;

import ru.otus.marchenko.dto.genre.GenreCreateDto;
import ru.otus.marchenko.dto.genre.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
    GenreDto findById(long id);
    void create(GenreCreateDto genreDto);
}