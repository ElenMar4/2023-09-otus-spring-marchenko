package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.dto.genre.GenreCreateDto;
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
    public void insert(GenreCreateDto genreDto) {
        genreRepository.save(new Genre(null, genreDto.name())); }
}