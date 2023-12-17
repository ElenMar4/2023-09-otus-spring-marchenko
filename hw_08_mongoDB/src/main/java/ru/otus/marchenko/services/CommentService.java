package ru.otus.marchenko.services;

import ru.otus.marchenko.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(String id);
    List<Comment> findAllByBookId(String bookId);
    Comment insert(String id, String message, String bookId);
    Comment update(String id, String message, String bookId);
    void deleteById(String id);
}