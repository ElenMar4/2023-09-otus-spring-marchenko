package ru.otus.marchenko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.marchenko.models.Genre;
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {}
