package ru.otus.marchenko.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.marchenko.models.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findBooksByAuthorId(String authorId);

    List<Book> findBooksByGenres(String genreId);
}