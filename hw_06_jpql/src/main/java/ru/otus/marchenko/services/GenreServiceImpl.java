package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.exceptions.EntityNotFoundException;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return genreRepository.findById(id);
    }

    @Transactional
    @Override
    public Genre insert(String genreName) {
        Optional<Genre> genre = genreRepository.findByName(genreName);
        return genre.orElseGet(() -> genreRepository.save(new Genre(null, genreName)));
    }

    @Transactional
    @Override
    public Genre deleteById(long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Genre not found"));
        genreRepository.deleteById(id);
        return genre;
    }
}
