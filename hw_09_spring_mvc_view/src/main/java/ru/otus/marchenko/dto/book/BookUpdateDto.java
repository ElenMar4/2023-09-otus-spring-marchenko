package ru.otus.marchenko.dto.book;

public record BookUpdateDto(
        Long id,
        String title,
        String authorName,
        String genreName) {
}
