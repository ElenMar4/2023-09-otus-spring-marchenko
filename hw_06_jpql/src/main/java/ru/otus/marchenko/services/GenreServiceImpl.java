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

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() { return genreRepository.findAll();}

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> findById(long id) { return genreRepository.findById(id);}

    @Transactional
    @Override
    public Genre insert(String genreName) { return genreRepository.save(new Genre(null, genreName)); }

    @Transactional
    @Override
    public Genre update(long id, String newGenreName) {
        Genre genre = findById(id).orElseThrow(() -> new EntityNotFoundException("Genre not found"));
            genre.setName(newGenreName);
            return genreRepository.save(genre);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }
}
