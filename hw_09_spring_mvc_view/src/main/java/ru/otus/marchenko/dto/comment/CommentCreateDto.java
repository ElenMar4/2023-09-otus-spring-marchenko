package ru.otus.marchenko.dto.comment;

public record CommentCreateDto(
        String message,
        Long bookId
) {
}
