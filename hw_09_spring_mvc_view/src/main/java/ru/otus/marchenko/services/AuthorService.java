package ru.otus.marchenko.services;

import ru.otus.marchenko.dto.author.AuthorCreateDto;
import ru.otus.marchenko.dto.author.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
    AuthorDto findBiId(long id);
    void create(AuthorCreateDto authorCreateDto);
}