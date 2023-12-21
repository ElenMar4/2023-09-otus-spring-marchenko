package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.dto.author.AuthorCreateDto;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() { return authorRepository.findAll();}

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findBiId(long id) { return authorRepository.findById(id);}

    @Transactional
    @Override
    public void insert(AuthorCreateDto authorDto) { authorRepository.save(new Author(null, authorDto.fullName()));}
}