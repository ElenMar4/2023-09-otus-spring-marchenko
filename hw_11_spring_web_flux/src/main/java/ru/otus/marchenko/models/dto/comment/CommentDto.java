package ru.otus.marchenko.models.dto.comment;

import ru.otus.marchenko.models.dto.book.BookDto;

public record CommentDto(
        String id,
        String message,
        String bookId
) {
}
