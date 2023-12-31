package ru.otus.marchenko.services;

import ru.otus.marchenko.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(long id);
    List<Comment> findAllByBookId(Long bookId);
    Comment insert(Long id, String message, Long bookId);
    Comment update(Long id, String message, Long bookId);
    void deleteById(long id);
}