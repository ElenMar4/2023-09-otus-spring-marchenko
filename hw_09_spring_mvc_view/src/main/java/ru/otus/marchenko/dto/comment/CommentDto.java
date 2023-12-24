package ru.otus.marchenko.dto.comment;

import ru.otus.marchenko.dto.book.BookDto;

public record CommentDto(
        Long id,
        String message,
        BookDto bookDto
) {
}
