package ru.otus.marchenko.models.mappers;

import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.genre.GenreCreateDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;

public interface GenreMapper {
    GenreDto toDto (Genre genre);
    Genre toModel (GenreCreateDto genreDto);
    Genre toModel (GenreDto genreDto);
}
