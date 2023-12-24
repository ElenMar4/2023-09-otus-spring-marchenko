package ru.otus.marchenko.services;

import ru.otus.marchenko.dto.book.BookDto;
import ru.otus.marchenko.dto.book.BookUpdateDto;
import ru.otus.marchenko.dto.book.BookCreateDto;

import java.util.List;

public interface BookService {
    List<BookDto> findAll();
    BookDto findById(long id);
    void create(BookCreateDto bookDto);
    void update(BookUpdateDto bookDto);
    void deleteById(long id);
}