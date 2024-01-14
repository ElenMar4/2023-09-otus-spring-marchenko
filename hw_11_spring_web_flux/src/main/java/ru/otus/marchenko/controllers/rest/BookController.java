package ru.otus.marchenko.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.book.BookCreateDto;
import ru.otus.marchenko.models.dto.book.BookDto;
import ru.otus.marchenko.models.dto.book.BookUpdateDto;
import ru.otus.marchenko.models.mappers.BookMapper;
import ru.otus.marchenko.repositories.AuthorReactiveRepository;
import ru.otus.marchenko.repositories.BookReactiveRepository;
import ru.otus.marchenko.repositories.GenreReactiveRepository;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookReactiveRepository bookRepository;
    private final AuthorReactiveRepository authorRepository;
    private final GenreReactiveRepository genreRepository;
    private final BookMapper bookMapper;

    @GetMapping("/api/v1/book")
    public Flux<BookDto> getAllBook() {
        return bookRepository.findAll()
                .map(bookMapper::toDto);
    }

    @GetMapping("/api/v1/book/{id}")
    public Mono<BookDto> getBookById(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto);
    }

    @DeleteMapping("/api/v1/book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return bookRepository.deleteById(id);
    }

    @PutMapping("/api/v1/book/{id}")
    public Mono<BookDto> updateBook(@RequestBody @Valid BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(bookUpdateDto.id()).block();
        Author author = authorRepository.findById(bookUpdateDto.authorId()).block();
        Genre genre = genreRepository.findById(bookUpdateDto.genreId()).block();
        return bookRepository.save(bookMapper.toModel(bookUpdateDto, book, author, genre))
                .map(bookMapper::toDto);
    }

    @PostMapping("/api/v1/book")
    public Mono<BookDto> createBook(@RequestBody @Valid BookCreateDto bookCreateDto) {
        Author author = authorRepository.findById(bookCreateDto.authorId()).block();
        Genre genre = genreRepository.findById(bookCreateDto.genreId()).block();
        return bookRepository.save(bookMapper.toModel(bookCreateDto, author, genre))
                .map(bookMapper::toDto);
    }
}
