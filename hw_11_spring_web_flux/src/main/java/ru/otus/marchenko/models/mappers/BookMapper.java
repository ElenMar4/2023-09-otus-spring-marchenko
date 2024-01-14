package ru.otus.marchenko.models.mappers;

import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.book.BookCreateDto;
import ru.otus.marchenko.models.dto.book.BookDto;
import ru.otus.marchenko.models.dto.book.BookUpdateDto;

public interface BookMapper {
    BookDto toDto (Book book);
    Book toModel (BookDto bookDto);
    Book toModel (BookCreateDto bookDto, Author author, Genre genre);
    Book toModel (BookUpdateDto bookDto, Book book, Author author, Genre genre);
}
