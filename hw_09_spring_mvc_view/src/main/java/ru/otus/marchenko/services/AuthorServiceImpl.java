package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.models.dto.author.AuthorCreateDto;
import ru.otus.marchenko.models.dto.author.AuthorDto;
import ru.otus.marchenko.exceptions.NotFoundException;
import ru.otus.marchenko.models.mappers.AuthorMapper;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public AuthorDto findBiId(long id) {
        Author author = authorRepository.findById(id).orElseThrow(()->new NotFoundException("Author not found"));
        return authorMapper.toDto(author);
    }

    @Transactional
    @Override
    public AuthorDto create(AuthorCreateDto authorDto) {
        Author author = authorRepository.save(authorMapper.toModel(authorDto));
        return authorMapper.toDto(author);
    }
}