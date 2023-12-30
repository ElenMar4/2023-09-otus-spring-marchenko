package ru.otus.marchenko.models.mappers;

import ru.otus.marchenko.models.dto.comment.CommentCreateDto;
import ru.otus.marchenko.models.dto.comment.CommentDto;
import ru.otus.marchenko.models.Comment;

public interface CommentMapper {
    CommentDto toDto (Comment comment);
    Comment toModel (CommentCreateDto commentDto);
    Comment toModel (CommentDto commentDto);
}
