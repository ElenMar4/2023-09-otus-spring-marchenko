package ru.otus.marchenko.services;

import ru.otus.marchenko.dto.comment.CommentCreateDto;
import ru.otus.marchenko.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findAllByBookId(Long bookId);
    void create(CommentCreateDto commentDto);
    void update(CommentDto commentDto);
    void deleteById(long id);
}