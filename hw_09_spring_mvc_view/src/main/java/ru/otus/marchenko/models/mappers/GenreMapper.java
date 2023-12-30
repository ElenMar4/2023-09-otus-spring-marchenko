package ru.otus.marchenko.models.mappers;

import ru.otus.marchenko.models.dto.genre.GenreCreateDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;
import ru.otus.marchenko.models.Genre;

public interface GenreMapper {
    GenreDto toDto (Genre genre);
    Genre toModel (GenreCreateDto genreDto);
    Genre toModel (GenreDto genreDto);
}
