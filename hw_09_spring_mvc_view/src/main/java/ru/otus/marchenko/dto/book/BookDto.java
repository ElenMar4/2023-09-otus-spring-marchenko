package ru.otus.marchenko.dto.book;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;

public record BookDto(
        Long id,
        String title,
        Author author,
        Genre genre) {

    public BookDto(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre());
    }
}
