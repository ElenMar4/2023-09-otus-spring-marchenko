package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.exceptions.EntityNotFoundException;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findBiId(long id) {
        return authorRepository.findById(id);
    }

    @Transactional
    @Override
    public Author insert(String authorName) {
        Optional<Author> authorOptional = authorRepository.findByName(authorName);
        return authorOptional.orElseGet(() -> authorRepository.save(new Author(null, authorName)));
    }

    @Transactional
    @Override
    public Author deleteById(long id) {
        Author removeAuthor = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        authorRepository.deleteById(id);
        return removeAuthor;
    }
}
