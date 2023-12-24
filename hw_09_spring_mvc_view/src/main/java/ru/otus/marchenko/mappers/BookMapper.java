package ru.otus.marchenko.mappers;

import ru.otus.marchenko.dto.book.BookCreateDto;
import ru.otus.marchenko.dto.book.BookDto;
import ru.otus.marchenko.dto.book.BookUpdateDto;
import ru.otus.marchenko.models.Book;

public interface BookMapper {
    BookDto toDto (Book book);
    BookUpdateDto toDto (BookDto book);
    Book toModel (BookDto bookDto);
    Book toModel (BookCreateDto bookDto);
    Book toModel (BookUpdateDto bookDto);
}
