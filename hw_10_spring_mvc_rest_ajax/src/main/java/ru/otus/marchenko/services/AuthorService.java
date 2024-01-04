package ru.otus.marchenko.services;

import ru.otus.marchenko.models.dto.author.AuthorCreateDto;
import ru.otus.marchenko.models.dto.author.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
    AuthorDto findBiId(long id);
    AuthorDto create(AuthorCreateDto authorCreateDto);
}