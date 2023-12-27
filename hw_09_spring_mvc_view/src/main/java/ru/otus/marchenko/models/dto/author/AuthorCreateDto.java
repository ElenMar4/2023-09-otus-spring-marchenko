package ru.otus.marchenko.models.dto.author;

import jakarta.validation.constraints.NotBlank;

public record AuthorCreateDto(
        @NotBlank(message = "Name field should not be blank") String fullName
) {
}
