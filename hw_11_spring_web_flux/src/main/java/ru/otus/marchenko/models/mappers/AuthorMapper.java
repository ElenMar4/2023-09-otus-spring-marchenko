package ru.otus.marchenko.models.mappers;

import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.dto.author.AuthorCreateDto;
import ru.otus.marchenko.models.dto.author.AuthorDto;

public interface AuthorMapper {
    AuthorDto toDto (Author author);
    Author toModel (AuthorCreateDto authorDto);
    Author toModel (AuthorDto authorDto);

}
