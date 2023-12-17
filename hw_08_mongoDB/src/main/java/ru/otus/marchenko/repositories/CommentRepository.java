package ru.otus.marchenko.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.marchenko.models.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByBook(String bookId);
}