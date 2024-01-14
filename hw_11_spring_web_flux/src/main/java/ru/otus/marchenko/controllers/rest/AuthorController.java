package ru.otus.marchenko.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.marchenko.models.dto.author.AuthorCreateDto;
import ru.otus.marchenko.models.dto.author.AuthorDto;
import ru.otus.marchenko.models.mappers.AuthorMapper;
import ru.otus.marchenko.repositories.AuthorReactiveRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorReactiveRepository repository;
    private final AuthorMapper mapper;

    @GetMapping("/api/v1/author")
    public Flux<AuthorDto> getAllAuthors() {
        return repository.findAll()
                .map(mapper::toDto);
    }

    @PostMapping("/api/v1/author")
    public Mono<AuthorDto> createAuthor(@Valid @RequestBody AuthorCreateDto authorCreateDto) {
        return repository.save(mapper.toModel(authorCreateDto))
                .map(mapper::toDto);
    }
}
