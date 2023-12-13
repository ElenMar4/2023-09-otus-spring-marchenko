package ru.otus.marchenko.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.marchenko.models.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {}