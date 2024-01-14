package ru.otus.marchenko.controllers.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import ru.otus.marchenko.models.dto.author.AuthorCreateDto;
import ru.otus.marchenko.models.dto.author.AuthorDto;
import ru.otus.marchenko.models.mappers.AuthorMapper;
import ru.otus.marchenko.repositories.AuthorReactiveRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorControllerTest {

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private AuthorReactiveRepository authorReactiveRepository;
    @Autowired
    private AuthorMapper authorMapper;

    private static final List<Author> AUTHOR_EXPECT = List.of(
            new Author("1L", "J. W. Goethe"),
            new Author("2L", "F. Dostoevsky"));
    private static final List<AuthorDto> AUTHOR_DTO_LIST = List.of(
            new AuthorDto("1L", "J. W. Goethe"),
            new AuthorDto("2L", "F. Dostoevsky")
    );
    private static final AuthorCreateDto AUTHOR_CREATE_DTO = new AuthorCreateDto("J. W. Goethe");

    @Test
    void shouldReturnCorrectAllAuthors() throws Exception {
        Flux<Author> authorsFlux = Flux.fromIterable(AUTHOR_EXPECT);
        Mockito
                .when(authorReactiveRepository.findAll())
                .thenReturn(authorsFlux);

        webClient.get().uri("/api/v1/author")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class)
                .hasSize(AUTHOR_EXPECT.size())
                .contains(AUTHOR_DTO_LIST.get(0),
                        AUTHOR_DTO_LIST.get(1));
    }

    @Test
    @DisplayName("Should correct create new author")
    public void shouldCorrectCreateAuthor() throws Exception {
        Mono<Author> authorMono = Mono.just(AUTHOR_EXPECT.get(0));
        Mockito
                .when(authorReactiveRepository.save(any()))
                .thenReturn(authorMono);
        var result = webClient.post()
                .uri("/api/v1/author")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(AUTHOR_CREATE_DTO))
                .exchange()
                .returnResult(AuthorDto.class)
                .getResponseBody().blockFirst();

        assertThat(result).isEqualTo(authorMapper.toDto(AUTHOR_EXPECT.get(0)));
    }
}