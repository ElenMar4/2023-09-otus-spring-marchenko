package ru.otus.marchenko.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import ru.otus.marchenko.models.Book;

public interface BookReactiveRepository extends ReactiveCrudRepository<Book, String> {
}
