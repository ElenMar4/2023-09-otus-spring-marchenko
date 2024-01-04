package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.exceptions.NotFoundException;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.genre.GenreCreateDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;
import ru.otus.marchenko.models.mappers.GenreMapper;
import ru.otus.marchenko.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream().map(genreMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public GenreDto findById(long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(()->new NotFoundException("Genre not found"));
        return genreMapper.toDto(genre);
    }

    @Transactional
    @Override
    public GenreDto create(GenreCreateDto genreDto) {
        Genre genre = genreRepository.save(genreMapper.toModel(genreDto));
    return genreMapper.toDto(genre);
    }
}