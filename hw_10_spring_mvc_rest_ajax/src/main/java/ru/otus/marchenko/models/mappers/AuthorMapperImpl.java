package ru.otus.marchenko.models.mappers;

import org.springframework.stereotype.Component;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.dto.author.AuthorCreateDto;
import ru.otus.marchenko.models.dto.author.AuthorDto;

@Component
public class AuthorMapperImpl implements AuthorMapper{

    @Override
    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }

    @Override
    public Author toModel(AuthorCreateDto authorDto) {
        return new Author(null, authorDto.fullName());
    }

    @Override
    public Author toModel(AuthorDto authorDto) {
        return new Author(authorDto.id(), authorDto.fullName());
    }
}
