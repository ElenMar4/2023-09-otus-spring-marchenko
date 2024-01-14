package ru.otus.marchenko.controllers.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.author.AuthorDto;
import ru.otus.marchenko.models.dto.book.BookCreateDto;
import ru.otus.marchenko.models.dto.book.BookDto;
import ru.otus.marchenko.models.dto.book.BookUpdateDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;
import ru.otus.marchenko.models.mappers.BookMapper;
import ru.otus.marchenko.repositories.AuthorReactiveRepository;
import ru.otus.marchenko.repositories.BookReactiveRepository;
import ru.otus.marchenko.repositories.GenreReactiveRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    @Autowired
    private WebTestClient webClient;
    @MockBean
    private BookReactiveRepository bookReactiveRepository;
    @MockBean
    private AuthorReactiveRepository authorReactiveRepository;
    @MockBean
    private GenreReactiveRepository genreReactiveRepository;
    @Autowired
    private BookMapper bookMapper;

    private static final List<AuthorDto> AUTHOR_DTO_EXPECT = List.of(
            new AuthorDto("1A", "J. W. Goethe"),
            new AuthorDto("2A", "F. Dostoevsky"));
    private static final List<Author> AUTHOR_EXPECT = List.of(
            new Author("1A", "J. W. Goethe"),
            new Author("2A", "F. Dostoevsky"));

    private static final List<GenreDto> GENRE_DTO_EXPECT = List.of(
            new GenreDto("1G", "Tragedy"),
            new GenreDto("2G", "Novel"));
    private static final List<Genre> GENRE_EXPECT = List.of(
            new Genre("1G", "Tragedy"),
            new Genre("2G", "Novel"));

    private static final List<BookDto> BOOK_DTO_EXPECT = List.of(
            new BookDto("1B", "Faust", AUTHOR_DTO_EXPECT.get(0), GENRE_DTO_EXPECT.get(0)),
            new BookDto("2B", "The Gambler", AUTHOR_DTO_EXPECT.get(1), GENRE_DTO_EXPECT.get(1)));
    private static final List<Book> BOOK_EXPECT = List.of(
            new Book("1B", "Faust", AUTHOR_EXPECT.get(0), GENRE_EXPECT.get(0)),
            new Book("2B", "The Gambler", AUTHOR_EXPECT.get(1), GENRE_EXPECT.get(1))
    );
    private static final String ID = "1B";
    private static final Book BOOK_UPDATE_EXPECT = new Book("1B", "New Title", AUTHOR_EXPECT.get(0), GENRE_EXPECT.get(0));
    private static final BookUpdateDto BOOK_UPDATE_DTO = new BookUpdateDto("1B", "New Title", "1A", "1G");
    private static final BookCreateDto BOOK_CREATE_EXPECT = new BookCreateDto("Faust", "1A", "1G");


    @Test
    @DisplayName("Should correct return list books")
    void shouldReturnCorrectBookList() throws Exception {
        Flux<Book> booksFlux = Flux.fromIterable(BOOK_EXPECT);
        Mockito
                .when(bookReactiveRepository.findAll())
                .thenReturn(booksFlux);

        List<BookDto> result = webClient.get().uri("/api/v1/book")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody()
                .collectList().block();

        assertThat(result).hasSize(BOOK_DTO_EXPECT.size())
                .contains(BOOK_DTO_EXPECT.get(0),
                        BOOK_DTO_EXPECT.get(1));
    }

    @Test
    @DisplayName("Should correct return book by id")
    void shouldReturnCorrectBookById() throws Exception {
        Mono<Book> bookMonoFindById = Mono.just(BOOK_EXPECT.get(0));
        Mockito
                .when(bookReactiveRepository.findById(BOOK_EXPECT.get(0).getId()))
                .thenReturn(bookMonoFindById);

        BookDto result = webClient.get().uri("/api/v1/book/{id}", ID)
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody()
                .blockFirst();

        assertThat(result).isEqualTo(BOOK_DTO_EXPECT.get(0));
    }

    @Test
    @DisplayName("Should correct delete book by id")
    void shouldCorrectDeleteBookById() throws Exception {
        Mono<Void> voidReturn = Mono.empty();
        Mockito
                .when(bookReactiveRepository.deleteById(ID))
                .thenReturn(voidReturn);

        webClient.delete().uri("/api/v1/book/{id}", ID)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("Should correct update book")
    void shouldUpdateBook() throws Exception {

        Mono<Book> bookUpdateMono = Mono.just(BOOK_UPDATE_EXPECT);
        Mono<Book> bookMono = Mono.just(BOOK_EXPECT.get(0));
        Mono<Author> authorMono = Mono.just(AUTHOR_EXPECT.get(0));
        Mono<Genre> genreMono = Mono.just(GENRE_EXPECT.get(0));

        Mockito.when(bookReactiveRepository.findById(any(String.class))).thenReturn(bookMono);
        Mockito.when(authorReactiveRepository.findById(any(String.class))).thenReturn(authorMono);
        Mockito.when(genreReactiveRepository.findById(any(String.class))).thenReturn(genreMono);
        Mockito.when(bookReactiveRepository.save(any())).thenReturn(bookUpdateMono);

        var result = webClient.put()
                .uri("/api/v1/book/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(BOOK_UPDATE_DTO))
                .exchange()
                .returnResult(BookDto.class)
                .getResponseBody().blockFirst();

        assertThat(result).isEqualTo(bookMapper.toDto(BOOK_UPDATE_EXPECT));
    }

    @Test
    @DisplayName("Should correct create new book")
    void shouldCreateNewBook() throws Exception {
        Mono<Author> authorMono = Mono.just(AUTHOR_EXPECT.get(0));
        Mono<Genre> genreMono = Mono.just(GENRE_EXPECT.get(0));
        Mono<Book> bookMonoCreate = Mono.just(BOOK_EXPECT.get(0));

        Mockito.when(authorReactiveRepository.findById(any(String.class))).thenReturn(authorMono);
        Mockito.when(genreReactiveRepository.findById(any(String.class))).thenReturn(genreMono);
        Mockito.when(bookReactiveRepository.save(any())).thenReturn(bookMonoCreate);

        var result = webClient.post()
                .uri("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(BOOK_CREATE_EXPECT))
                .exchange()
                .returnResult(BookDto.class)
                .getResponseBody().blockFirst();

        assertThat(result).isEqualTo(bookMapper.toDto(BOOK_EXPECT.get(0)));
    }
}