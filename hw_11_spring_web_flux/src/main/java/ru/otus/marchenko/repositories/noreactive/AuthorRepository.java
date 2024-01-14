package ru.otus.marchenko.repositories.noreactive;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;
import ru.otus.marchenko.models.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {}