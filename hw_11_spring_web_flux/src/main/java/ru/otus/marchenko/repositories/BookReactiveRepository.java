package ru.otus.marchenko.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import org.springframework.stereotype.Repository;
import ru.otus.marchenko.models.Book;
@Repository
public interface BookReactiveRepository extends ReactiveCrudRepository<Book, String> {
}
