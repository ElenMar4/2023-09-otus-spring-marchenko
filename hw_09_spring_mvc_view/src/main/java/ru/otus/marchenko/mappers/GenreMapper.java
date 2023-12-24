package ru.otus.marchenko.mappers;

import ru.otus.marchenko.dto.genre.GenreCreateDto;
import ru.otus.marchenko.dto.genre.GenreDto;
import ru.otus.marchenko.models.Genre;

public interface GenreMapper {
    GenreDto toDto (Genre genre);
    Genre toModel (GenreCreateDto genreDto);
    Genre toModel (GenreDto genreDto);
}
