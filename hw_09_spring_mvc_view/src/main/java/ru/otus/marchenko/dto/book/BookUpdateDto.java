package ru.otus.marchenko.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookUpdateDto(
        Long id,
        @NotBlank(message = "Title field should not be blank") String title,
        @NotNull(message = "Author field should not be null") Long authorId,
        @NotNull(message = "Genre field should not be null") Long genreId) {
}
