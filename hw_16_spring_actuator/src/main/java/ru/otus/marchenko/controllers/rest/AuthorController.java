package ru.otus.marchenko.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.marchenko.models.dto.author.AuthorCreateDto;
import ru.otus.marchenko.models.dto.author.AuthorDto;
import ru.otus.marchenko.services.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/api/v1/author")
    public List<AuthorDto> getAllAuthors() {
        return authorService.findAll();
    }

    @PostMapping("/api/v1/author")
    public AuthorDto createAuthor(@Valid @RequestBody AuthorCreateDto authorCreateDto) {
        return authorService.create(authorCreateDto);
    }
}
