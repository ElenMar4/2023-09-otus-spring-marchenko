package ru.otus.marchenko.dto.book;
import ru.otus.marchenko.dto.author.AuthorDto;
import ru.otus.marchenko.dto.genre.GenreDto;

public record BookDto(
        Long id,
        String title,
        AuthorDto authorDto,
        GenreDto genreDto) {
}
