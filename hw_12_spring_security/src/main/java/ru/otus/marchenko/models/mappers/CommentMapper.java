package ru.otus.marchenko.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;
import ru.otus.marchenko.models.dto.comment.CommentCreateDto;
import ru.otus.marchenko.models.dto.comment.CommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "bookId", source = "book.id")
    CommentDto toDto (Comment comment);

    @Mappings({
            @Mapping(target = "book", source = "book"),
            @Mapping(target = "id", expression = "java(null)")
    })
    Comment toModel (CommentCreateDto commentCreateDto, Book book);


    @Mapping(target = "id", source = "commentDto.id")
    Comment toModel (CommentDto commentDto, Book book);
}
