package ru.otus.marchenko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.marchenko.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
