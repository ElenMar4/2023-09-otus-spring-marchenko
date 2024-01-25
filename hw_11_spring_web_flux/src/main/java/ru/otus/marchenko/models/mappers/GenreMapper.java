package ru.otus.marchenko.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.genre.GenreCreateDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto toDto (Genre genre);

    Genre toModel (GenreDto genreDto);

    @Mappings({
            @Mapping(target = "id", expression = "java(null)"),
            @Mapping(target = "name", source = "genreDto.name")

    })
    Genre toModel (GenreCreateDto genreDto);
}
