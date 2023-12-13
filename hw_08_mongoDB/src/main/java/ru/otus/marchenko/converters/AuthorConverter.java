package ru.otus.marchenko.converters;

import org.springframework.stereotype.Component;
import ru.otus.marchenko.models.Author;

@Component
public class AuthorConverter {
    public String authorToString(Author author) {
        if(author == null){
            return "unknown";
        }
        return "Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}