package ru.otus.marchenko.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.marchenko.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genres"})
    List<Book> findAll();

    @EntityGraph(attributePaths = {"author", "genres"})
    Optional<Book> findById(long id);
}
