package ru.otus.marchenko.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.marchenko.models.dto.genre.GenreCreateDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;
import ru.otus.marchenko.models.mappers.GenreMapper;
import ru.otus.marchenko.repositories.GenreReactiveRepository;


@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreReactiveRepository repository;
    private final GenreMapper mapper;

    @GetMapping("/api/v1/genre")
    public Flux<GenreDto> getAllGenres() {
        return repository.findAll().map(mapper::toDto);
    }

    @PostMapping("/api/v1/genre")
    public Mono<GenreDto> createAuthor(@Valid @RequestBody GenreCreateDto genreCreateDto) {
        return repository.save(mapper.toModel(genreCreateDto))
                .map(mapper::toDto);
    }
}
