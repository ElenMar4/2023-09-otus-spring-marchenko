package ru.otus.marchenko.repositories;

import ru.otus.marchenko.models.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreRepository {
    List<Genre> findAll();

    Optional<Genre> findById(long id);
    List<Genre> findAllByIds(Set<Long> ids);
    Optional<Genre> findByName(String genreName);
    Genre save(Genre genre);
    Genre deleteById(long id);
}
