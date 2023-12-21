package ru.otus.marchenko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.marchenko.models.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByFullName(String name);
}
