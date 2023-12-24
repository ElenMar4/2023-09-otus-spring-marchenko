package ru.otus.marchenko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.marchenko.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {}