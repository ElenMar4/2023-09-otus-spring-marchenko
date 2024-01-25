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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.comment.CommentCreateDto;
import ru.otus.marchenko.models.dto.comment.CommentDto;
import ru.otus.marchenko.models.mappers.CommentMapper;
import ru.otus.marchenko.repositories.BookReactiveRepository;
import ru.otus.marchenko.repositories.CommentReactiveRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@WebFluxTest
@ContextConfiguration(classes = {CommentController.class})
class CommentControllerTest {

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private BookReactiveRepository bookReactiveRepository;
    @MockBean
    private CommentReactiveRepository commentReactiveRepository;
    @MockBean
    private CommentMapper commentMapper;

    private static final Author AUTHOR_EXPECT = new Author("1A", "J. W. Goethe");
    private static final Genre GENRE_EXPECT = new Genre("1G", "Tragedy");
    private static final Book BOOK_EXPECT = new Book("1B", "Faust", AUTHOR_EXPECT, GENRE_EXPECT);
    private final static List<Comment> COMMENTS_EXPECTED = List.of(new Comment("1C", "ok", BOOK_EXPECT));
    private final static List<CommentDto> COMMENTS_DTO_EXPECTED = List.of(new CommentDto("1C", "ok", BOOK_EXPECT.getId()));
    private final static CommentCreateDto COMMENTS_CREATED = new CommentCreateDto("ok", BOOK_EXPECT.getId());

    @Test
    @DisplayName("Should correct return list comments by book")
    void shouldReturnCorrectListCommentsByBookId() throws Exception {
        Flux<Comment> commentsFlux = Flux.fromIterable(COMMENTS_EXPECTED);
        Mockito
                .when(commentReactiveRepository.findAllByBookId(BOOK_EXPECT.getId()))
                .thenReturn(commentsFlux);
        Mockito
                .when(commentMapper.toDto(any()))
                .thenReturn(COMMENTS_DTO_EXPECTED.get(0));
        webClient.get().uri("/api/v1/comment/{id}", BOOK_EXPECT.getId())
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class)
                .hasSize(COMMENTS_EXPECTED.size())
                .contains(COMMENTS_DTO_EXPECTED.get(0));
    }

    @Test
    @DisplayName("Should correct create new comment by book")
    void shouldCreateNewCommentByBook() throws Exception {
        Mono<Comment> commentMono = Mono.just(COMMENTS_EXPECTED.get(0));
        Mono<Book> bookMono = Mono.just(BOOK_EXPECT);

        Mockito.when(bookReactiveRepository.findById(any(String.class))).thenReturn(bookMono);
        Mockito.when(commentReactiveRepository.save(any())).thenReturn(commentMono);
        Mockito
                .when(commentMapper.toDto(any()))
                .thenReturn(COMMENTS_DTO_EXPECTED.get(0));
        var result = webClient.post()
                .uri("/api/v1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(COMMENTS_CREATED))
                .exchange()
                .returnResult(CommentDto.class)
                .getResponseBody().blockFirst();

        assertThat(result).isEqualTo(commentMapper.toDto(COMMENTS_EXPECTED.get(0)));
    }

    @Test
    @DisplayName("Should correct delete comment by id")
    void shouldCorrectDeleteCommentById() throws Exception {
        Mono<Void> voidReturn = Mono.empty();
        Mockito
                .when(commentReactiveRepository.deleteById(any(String.class)))
                .thenReturn(voidReturn);
        Mockito
                .when(commentMapper.toDto(any()))
                .thenReturn(COMMENTS_DTO_EXPECTED.get(0));
        webClient.delete().uri("/api/v1/comment/{id}", BOOK_EXPECT.getId())
                .exchange()
                .expectStatus().isNoContent();
    }
}