package ru.otus.marchenko.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.marchenko.models.Comment;

public interface CommentReactiveRepository extends ReactiveCrudRepository<Comment, String> {
    Flux<Comment> findAllByBookId(String bookId);
}