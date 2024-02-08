package ru.otus.marchenko.models.dto.comment;

public record CommentDto(
        Long id,
        String message,
        Long bookId
) {
}
