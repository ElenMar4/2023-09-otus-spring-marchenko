package ru.otus.marchenko.repositories;

import ru.otus.marchenko.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(long id);

    List<Comment> findAllByBookId(long bookId);

    Comment save(Comment comment);

    void deleteById(long id);
}
