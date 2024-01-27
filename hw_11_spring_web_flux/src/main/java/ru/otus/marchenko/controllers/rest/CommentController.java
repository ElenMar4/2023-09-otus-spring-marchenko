package ru.otus.marchenko.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.marchenko.exceptions.NotFoundException;
import ru.otus.marchenko.models.dto.comment.CommentCreateDto;
import ru.otus.marchenko.models.dto.comment.CommentDto;
import ru.otus.marchenko.models.mappers.CommentMapper;
import ru.otus.marchenko.repositories.BookReactiveRepository;
import ru.otus.marchenko.repositories.CommentReactiveRepository;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentReactiveRepository commentRepository;
    private final BookReactiveRepository bookRepository;
    private final CommentMapper mapper;

    @GetMapping("/api/v1/comment/{id}")
    public Flux<CommentDto> getCommentsByBook(@PathVariable("id") String bookId) {
        return commentRepository.findAllByBookId(bookId)
                .map(mapper::toDto);
    }

    @PostMapping("/api/v1/comment")
    public Mono<CommentDto> createComment(@Valid @RequestBody CommentCreateDto commentCreateDto) {
        return bookRepository.findById(commentCreateDto.getBookId())
                .switchIfEmpty(Mono.error(new NotFoundException("Not found book")))
                .flatMap(data ->{
                    return commentRepository.save(mapper.toModel(commentCreateDto, data))
                            .map(mapper::toDto);
                });

    }

    @DeleteMapping("/api/v1/comment/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteComment(@PathVariable("id") String id) {
        return commentRepository.deleteById(id);
    }
}
