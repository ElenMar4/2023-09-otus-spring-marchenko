package ru.otus.marchenko.services;

import ru.otus.marchenko.dto.comment.CommentCreateDto;
import ru.otus.marchenko.dto.comment.CommentDto;
import ru.otus.marchenko.models.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDto> findAllByBookId(Long bookId);
    void insert(CommentCreateDto commentDto);
    void update(Comment comment);
    void deleteById(long id);
}