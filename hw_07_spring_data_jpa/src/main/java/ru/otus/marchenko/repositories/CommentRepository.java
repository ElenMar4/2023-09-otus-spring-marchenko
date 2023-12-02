package ru.otus.marchenko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.marchenko.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {}