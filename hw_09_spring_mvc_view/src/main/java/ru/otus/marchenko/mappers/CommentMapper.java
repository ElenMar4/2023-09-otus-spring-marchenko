package ru.otus.marchenko.mappers;

import ru.otus.marchenko.dto.comment.CommentCreateDto;
import ru.otus.marchenko.dto.comment.CommentDto;
import ru.otus.marchenko.models.Comment;

public interface CommentMapper {
    CommentDto toDto (Comment comment);
    Comment toModel (CommentCreateDto commentDto);
    Comment toModel (CommentDto commentDto);
}
