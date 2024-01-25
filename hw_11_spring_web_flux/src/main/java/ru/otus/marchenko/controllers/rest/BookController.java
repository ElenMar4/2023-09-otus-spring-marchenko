package ru.otus.marchenko.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.marchenko.exceptions.NotFoundException;
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
                .switchIfEmpty(Mono.error(new NotFoundException("Not found book")))
                .map(bookMapper::toDto);
    }

    @DeleteMapping("/api/v1/book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return bookRepository.deleteById(id);
    }

    @PutMapping("/api/v1/book/{id}")
    public Mono<BookDto> updateBook(@RequestBody @Valid BookUpdateDto bookUpdateDto) {
        return bookRepository.findById(bookUpdateDto.getId())
                .switchIfEmpty(Mono.error(new NotFoundException("Not found book")))
                .zipWith(authorRepository.findById(bookUpdateDto.getAuthorId()))
                .switchIfEmpty(Mono.error(new NotFoundException("Not found author")))
                .zipWith(genreRepository.findById(bookUpdateDto.getGenreId()))
                .switchIfEmpty(Mono.error(new NotFoundException("Not found genre")))
                .flatMap(data ->{
                    return bookRepository.save(bookMapper
                                    .toModel(bookUpdateDto, data.getT1().getT2(), data.getT2()))
                                    .map(bookMapper::toDto);
                });
    }

    @PostMapping("/api/v1/book")
    public Mono<BookDto> createBook(@RequestBody @Valid BookCreateDto bookCreateDto) {
            return authorRepository.findById(bookCreateDto.getAuthorId())
                    .switchIfEmpty(Mono.error(new NotFoundException("Not found author")))
                    .zipWith(genreRepository.findById(bookCreateDto.getGenreId()))
                    .switchIfEmpty(Mono.error(new NotFoundException("Not found genre")))
                    .flatMap(data -> {
                        return bookRepository.save(bookMapper
                                        .toModel(bookCreateDto, data.getT1(), data.getT2()))
                                .map(bookMapper::toDto);
                    });
    }
}
