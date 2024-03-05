package ru.otus.marchenko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.marchenko.models.Genre;
@RepositoryRestResource(path = "genre")
public interface GenreRepository extends JpaRepository<Genre, Long> {}
