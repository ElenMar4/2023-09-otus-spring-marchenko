package ru.otus.marchenko.repositories.noreactive;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.marchenko.models.Genre;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {}