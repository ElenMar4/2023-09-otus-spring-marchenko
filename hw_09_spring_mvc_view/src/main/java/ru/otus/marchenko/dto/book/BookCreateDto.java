package ru.otus.marchenko.dto.book;

import jakarta.validation.constraints.NotBlank;

public record BookCreateDto(
        @NotBlank(message = "Title field should not be blank") String title,
        @NotBlank(message = "Author field should not be blank") String authorName,
        @NotBlank(message = "Genre field should not be blank") String genreName) {
}
