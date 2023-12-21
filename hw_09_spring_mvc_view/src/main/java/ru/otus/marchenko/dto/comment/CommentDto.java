package ru.otus.marchenko.dto.comment;

import ru.otus.marchenko.models.Comment;

public record CommentDto(
        Long id,
        String message,
        Long bookId
) {
    public CommentDto(Comment comment){
        this(comment.getId(), comment.getMessage(), comment.getBook().getId());
    }
}
