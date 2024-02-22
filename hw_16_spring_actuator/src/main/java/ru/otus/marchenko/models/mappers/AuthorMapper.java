package ru.otus.marchenko.models.mappers;

import org.mapstruct.Mapper;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.dto.author.AuthorCreateDto;
import ru.otus.marchenko.models.dto.author.AuthorDto;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto (Author author);

    Author toModel (AuthorCreateDto authorDto);

    Author toModel (AuthorDto authorDto);

}
