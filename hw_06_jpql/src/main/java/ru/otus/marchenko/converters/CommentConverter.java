package ru.otus.marchenko.converters;

import org.springframework.stereotype.Component;
import ru.otus.marchenko.models.Comment;

@Component
public class CommentConverter {

    public String commentToString(Comment comment){
        return "Id: %d, Message: %s".formatted(comment.getId(), comment.getMessage());
    }
}
