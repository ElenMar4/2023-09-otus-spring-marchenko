package ru.otus.marchenko.controllers.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.book.BookCreateDto;
import ru.otus.marchenko.models.dto.comment.CommentCreateDto;
import ru.otus.marchenko.models.mappers.*;
import ru.otus.marchenko.repositories.AuthorReactiveRepository;
import ru.otus.marchenko.repositories.BookReactiveRepository;
import ru.otus.marchenko.repositories.CommentReactiveRepository;
import ru.otus.marchenko.repositories.GenreReactiveRepository;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest
@ContextConfiguration(classes = {RestControllerExceptionHandler.class,
                                    BookController.class,
                                    AuthorController.class,
                                    GenreController.class})
class RestControllerExceptionHandlerTest {

    @MockBean
    private AuthorReactiveRepository authorRepository;
    @MockBean
    private BookReactiveRepository bookRepository;
    @MockBean
    private GenreReactiveRepository genreRepository;
    @MockBean
    private CommentReactiveRepository commentRepository;
    @MockBean
    private BookMapper bookMapper;
    @MockBean
    private AuthorMapper authorMapper;
    @MockBean
    private GenreMapper genreMapper;
    @MockBean
    private CommentMapper commentMapper;
    @Autowired
    private WebTestClient webClient;

    //____________________404_________________________
    @Test
    @DisplayName("Correct return httpStatus=404, when book not found")
    void shouldHandleNotFoundExceptionForBook() {
        Mockito
                .when(bookRepository.findById(any(String.class)))
                .thenReturn(Mono.empty());

        webClient.get().uri("/api/v1/book/{id}", "not_found_book")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Correct return httpStatus=404, when author not found by create book")
    void shouldHandleNotFoundExceptionForAuthorByCreateBook() {
        Mockito
                .when(authorRepository.findById(any(String.class)))
                .thenReturn(Mono.empty());
        Mockito
                .when(genreRepository.findById(any(String.class)))
                .thenReturn(Mono.just(new Genre()));

        webClient.post()
                .uri("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new BookCreateDto("title", "not_found_id_by_author", "any")))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Correct return httpStatus=404, when genre not found by create book")
    void shouldHandleNotFoundExceptionForGenreByCreateBook() {
        Mockito
                .when(authorRepository.findById(any(String.class)))
                .thenReturn(Mono.just(new Author()));
        Mockito
                .when(genreRepository.findById(any(String.class)))
                .thenReturn(Mono.empty());

        webClient.post()
                .uri("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new BookCreateDto("title", "any", "not_found_id_by_genre")))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Correct return httpStatus=404, when book not found by create comment")
    void shouldHandleNotFoundExceptionForBookByCreateComment() {
        Mockito
                .when(bookRepository.findById(any(String.class)))
                .thenReturn(Mono.empty());

        webClient.post()
                .uri("/api/v1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new CommentCreateDto("message", "not_found_id_by_book")))
                .exchange()
                .expectStatus().isNotFound();

    }
//    ____________________500_________________________
    @Test
    @DisplayName("Correct return httpStatus=500, when server throw RuntimeException")
    void shouldHandle() {
        Mockito
                .when(bookRepository.findAll())
                .thenThrow(new RuntimeException());
        webClient.get()
                .uri("/api/v1/book").exchange()
                .expectStatus()
                .is5xxServerError();
    }
    //____________________500________________________
    @Test
    @DisplayName("Correct return httpStatus=400, when client enter incorrect request")
    void shouldHandleRuntimeException() {

        webClient.post()
                .uri("/api/v1/book")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new BookCreateDto(null, null, null)))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}