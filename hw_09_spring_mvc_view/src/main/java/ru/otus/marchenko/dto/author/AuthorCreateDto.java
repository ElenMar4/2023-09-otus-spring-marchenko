package ru.otus.marchenko.dto.author;

import jakarta.validation.constraints.NotBlank;

public record AuthorCreateDto(
        @NotBlank(message = "Name field should not be blank") String fullName
) {
}
