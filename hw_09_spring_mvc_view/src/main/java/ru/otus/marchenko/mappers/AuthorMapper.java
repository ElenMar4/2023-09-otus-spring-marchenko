package ru.otus.marchenko.mappers;

import ru.otus.marchenko.dto.author.AuthorCreateDto;
import ru.otus.marchenko.dto.author.AuthorDto;
import ru.otus.marchenko.models.Author;

public interface AuthorMapper {
    AuthorDto toDto (Author author);
    Author toModel (AuthorCreateDto authorDto);
    Author toModel (AuthorDto authorDto);

}
