package ru.otus.marchenko.services;

import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findById(long id);

    Book insert(String title, Long authorId, Set<Long> genresIds);

    Book update(Long id, String title, Long authorId, Set<Long> genresIds);

    void deleteById(long id);

    List<Comment> findCommentsByBook(long bookId);
}
