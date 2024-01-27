package ru.otus.marchenko.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.marchenko.models.dto.book.BookCreateDto;
import ru.otus.marchenko.models.dto.book.BookDto;
import ru.otus.marchenko.models.dto.book.BookUpdateDto;
import ru.otus.marchenko.services.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/v1/book")
    public List<BookDto> getAllBook(){
        return bookService.findAll();
    }

    @GetMapping("/api/v1/book/{id}")
    public BookDto getBookById(@PathVariable("id") Long id){
        return bookService.findById(id);
    }

    @DeleteMapping("/api/v1/book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/api/v1/book/{id}")
    public BookDto updateBook(@RequestBody @Valid BookUpdateDto bookUpdateDto) {
        return bookService.update(bookUpdateDto);
    }

    @PostMapping("/api/v1/book")
    public BookDto createBook(@RequestBody @Valid BookCreateDto bookCreateDto){
        return bookService.create(bookCreateDto);
    }
}
