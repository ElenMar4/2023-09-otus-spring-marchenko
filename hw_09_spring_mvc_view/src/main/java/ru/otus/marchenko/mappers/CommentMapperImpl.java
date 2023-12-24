package ru.otus.marchenko.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.marchenko.dto.book.BookDto;
import ru.otus.marchenko.dto.comment.CommentCreateDto;
import ru.otus.marchenko.dto.comment.CommentDto;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;

@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements CommentMapper{

    private final BookMapper bookMapper;

    @Override
    public CommentDto toDto(Comment comment) {
        BookDto bookDto = bookMapper.toDto(comment.getBook());
        return new CommentDto(comment.getId(), comment.getMessage(), bookDto);
    }

    @Override
    public Comment toModel(CommentCreateDto commentDto) {
        return new Comment(null, commentDto.message(), null);
    }

    @Override
    public Comment toModel(CommentDto commentDto) {
        Book book = bookMapper.toModel(commentDto.bookDto());
        return new Comment(commentDto.id(), commentDto.message(), book);
    }
}
