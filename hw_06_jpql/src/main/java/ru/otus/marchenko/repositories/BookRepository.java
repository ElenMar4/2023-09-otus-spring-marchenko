package ru.otus.marchenko.repositories;

import ru.otus.marchenko.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(long id);

    Optional<Book> findByTitleAndAuthor(String title, Long authorId);

    Book save(Book book);

    void deleteById(long id);
}
