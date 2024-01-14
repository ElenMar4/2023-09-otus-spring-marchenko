package ru.otus.marchenko.repositories.noreactive;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.marchenko.models.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}