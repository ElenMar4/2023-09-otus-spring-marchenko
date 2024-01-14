package ru.otus.marchenko.models.mappers;

import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;
import ru.otus.marchenko.models.dto.comment.CommentCreateDto;
import ru.otus.marchenko.models.dto.comment.CommentDto;

public interface CommentMapper {
    CommentDto toDto (Comment comment);
    Comment toModel (CommentCreateDto commentCreateDto, Book book);
    Comment toModel (CommentDto commentDto, Book book);
}
