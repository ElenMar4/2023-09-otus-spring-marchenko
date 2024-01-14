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
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.genre.GenreCreateDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;
import ru.otus.marchenko.models.mappers.GenreMapper;
import ru.otus.marchenko.repositories.GenreReactiveRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GenreControllerTest {

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private GenreReactiveRepository genreReactiveRepository;
    @Autowired
    private GenreMapper genreMapper;

    private static final List<GenreDto> GENRE_DTO_EXPECT = List.of(
            new GenreDto("1L", "Tragedy"),
            new GenreDto("2L", "Novel"));
    private static final List<Genre> GENRE_EXPECT = List.of(
            new Genre("1L", "Tragedy"),
            new Genre("2L", "Novel"));
    private static final GenreCreateDto GENRE_CREATE_DTO = new GenreCreateDto("Tragedy");

    @Test
    @DisplayName("Should correct return list genres")
    void shouldReturnCorrectGenreList() throws Exception {
        Flux<Genre> genresFlux = Flux.fromIterable(GENRE_EXPECT);
        Mockito.when(genreReactiveRepository.findAll()).thenReturn(genresFlux);

        webClient.get().uri("/api/v1/genre")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GenreDto.class)
                .hasSize(GENRE_EXPECT.size())
                .contains(GENRE_DTO_EXPECT.get(0),
                        GENRE_DTO_EXPECT.get(1));
    }

    @Test
    @DisplayName("Should correct create new genre")
    public void shouldCorrectCreateGenre() throws Exception {
        Mono<Genre> genreMono = Mono.just(GENRE_EXPECT.get(0));
        Mockito.when(genreReactiveRepository.save(any())).thenReturn(genreMono);
        var result = webClient.post()
                .uri("/api/v1/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(GENRE_CREATE_DTO))
                .exchange()
                .returnResult(GenreDto.class)
                .getResponseBody().blockFirst();

        assertThat(result).isEqualTo(genreMapper.toDto(GENRE_EXPECT.get(0)));
    }
}