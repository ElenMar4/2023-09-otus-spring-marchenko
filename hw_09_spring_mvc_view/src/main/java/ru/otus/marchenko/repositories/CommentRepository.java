package ru.otus.marchenko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.marchenko.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBookId(Long BookId);
}