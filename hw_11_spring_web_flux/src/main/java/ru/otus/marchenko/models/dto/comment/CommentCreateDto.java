package ru.otus.marchenko.models.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateDto(
        @NotBlank(message = "Message field should not be blank") String message,
        @NotNull(message = "Book field should not be null")String bookId
) {
}
