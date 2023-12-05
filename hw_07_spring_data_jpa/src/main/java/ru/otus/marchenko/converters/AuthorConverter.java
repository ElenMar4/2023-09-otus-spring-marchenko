package ru.otus.marchenko.converters;

import org.springframework.stereotype.Component;
import ru.otus.marchenko.models.Author;

@Component
public class AuthorConverter {
    public String authorToString(Author author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}