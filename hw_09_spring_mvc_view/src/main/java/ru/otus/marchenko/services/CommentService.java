package ru.otus.marchenko.services;

import ru.otus.marchenko.models.dto.comment.CommentCreateDto;
import ru.otus.marchenko.models.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findAllByBookId(Long bookId);
    CommentDto create(CommentCreateDto commentDto);
    CommentDto update(CommentDto commentDto);
    void deleteById(long id);
}