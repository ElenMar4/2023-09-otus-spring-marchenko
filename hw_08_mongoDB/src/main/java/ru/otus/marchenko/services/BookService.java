package ru.otus.marchenko.services;

import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(String id);
    List<Comment> findCommentsByBook(String bookId);
    Book insert(String title, String authorId, Set<String> genresIds);
    Book update(String id, String title, String authorId, Set<String> genresIds);
    void deleteById(String id);
}