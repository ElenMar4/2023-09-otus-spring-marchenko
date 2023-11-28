package ru.otus.marchenko.repositories;

import ru.otus.marchenko.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(long id);

    Optional<Book> findCopyByTitleAndAuthor(String title, Long authorId);

    Book save(Book book);

    Book deleteById(long id);
}
