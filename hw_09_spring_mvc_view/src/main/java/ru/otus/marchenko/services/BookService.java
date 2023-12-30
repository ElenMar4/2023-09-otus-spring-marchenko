package ru.otus.marchenko.services;

import ru.otus.marchenko.models.dto.book.BookDto;
import ru.otus.marchenko.models.dto.book.BookUpdateDto;
import ru.otus.marchenko.models.dto.book.BookCreateDto;

import java.util.List;

public interface BookService {
    List<BookDto> findAll();
    BookDto findById(long id);
    BookDto create(BookCreateDto bookDto);
    BookDto update(BookUpdateDto bookDto);
    void deleteById(long id);
}