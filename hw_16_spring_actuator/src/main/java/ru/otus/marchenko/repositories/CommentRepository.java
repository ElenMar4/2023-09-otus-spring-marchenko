package ru.otus.marchenko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.marchenko.models.Comment;

import java.util.List;
@RepositoryRestResource(path = "comment")
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBookId(Long BookId);
}
