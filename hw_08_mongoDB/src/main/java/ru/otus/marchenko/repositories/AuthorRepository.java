package ru.otus.marchenko.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.marchenko.models.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {}