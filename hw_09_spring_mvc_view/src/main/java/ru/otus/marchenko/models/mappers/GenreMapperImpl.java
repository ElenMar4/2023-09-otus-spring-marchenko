package ru.otus.marchenko.models.mappers;

import org.springframework.stereotype.Component;
import ru.otus.marchenko.models.dto.genre.GenreCreateDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;
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
