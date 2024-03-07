package ru.otus.marchenko.models.dto.genre;

import jakarta.validation.constraints.NotBlank;

public record GenreCreateDto(
        @NotBlank(message = "Name field should not be blank") String name
) {
}
