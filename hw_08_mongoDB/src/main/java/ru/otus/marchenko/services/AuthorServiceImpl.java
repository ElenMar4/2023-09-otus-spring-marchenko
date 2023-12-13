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

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() { return authorRepository.findAll();}

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findBiId(String id) { return authorRepository.findById(id);}

    @Transactional
    @Override
    public Author insert(String authorName) { return  authorRepository.save(new Author(null, authorName));}

    @Transactional
    @Override
    public Author update(String id, String newAuthorName) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
            author.setFullName(newAuthorName);
            return authorRepository.save(author);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }
}