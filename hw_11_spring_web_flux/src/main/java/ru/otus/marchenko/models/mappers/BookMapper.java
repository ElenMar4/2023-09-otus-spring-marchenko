package ru.otus.marchenko.models.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.book.BookCreateDto;
import ru.otus.marchenko.models.dto.book.BookDto;
import ru.otus.marchenko.models.dto.book.BookUpdateDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {AuthorMapper.class, GenreMapper.class})
public interface BookMapper {

    @Mappings({
            @Mapping(target = "authorDto", source = "book.author"),
            @Mapping(target = "genreDto", source = "book.genre")
    })
    BookDto toDto (Book book);

    @Mapping(target = "id", source = "bookDto.id")
    Book toModel (BookDto bookDto, Author author, Genre genre);

    @Mappings({
            @Mapping(target = "id", expression = "java(null)"),
            @Mapping(target = "title", source = "bookCreateDto.title"),
            @Mapping(target = "author", source = "author"),
            @Mapping(target = "genre", source = "genre") })
    Book toModel (BookCreateDto bookCreateDto, Author author, Genre genre);

    @Mappings({
            @Mapping(target = "id", source = "bookUpdateDto.id"),
            @Mapping(target = "title", source = "bookUpdateDto.title"),
            @Mapping(target = "author", source = "author"),
            @Mapping(target = "genre", source = "genre")
    })
    Book toModel (BookUpdateDto bookUpdateDto, Author author, Genre genre);
}
