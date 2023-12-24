package ru.otus.marchenko.mappers;

import org.springframework.stereotype.Component;
import ru.otus.marchenko.dto.genre.GenreCreateDto;
import ru.otus.marchenko.dto.genre.GenreDto;
import ru.otus.marchenko.models.Genre;

@Component
public class GenreMapperImpl implements GenreMapper{

    @Override
    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    @Override
    public Genre toModel(GenreCreateDto genreDto) {
        return new Genre(null, genreDto.name());
    }

    @Override
    public Genre toModel(GenreDto genreDto) {
        return new Genre(genreDto.id(), genreDto.name());
    }
}
