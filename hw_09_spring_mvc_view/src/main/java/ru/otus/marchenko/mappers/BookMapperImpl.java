package ru.otus.marchenko.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.marchenko.dto.author.AuthorDto;
import ru.otus.marchenko.dto.book.BookCreateDto;
import ru.otus.marchenko.dto.book.BookDto;
import ru.otus.marchenko.dto.book.BookUpdateDto;
import ru.otus.marchenko.dto.genre.GenreDto;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;

@Component
@RequiredArgsConstructor
public class BookMapperImpl implements BookMapper{

    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;

    @Override
    public BookDto toDto(Book book) {
        AuthorDto authorDto = authorMapper.toDto(book.getAuthor());
        GenreDto genreDto = genreMapper.toDto(book.getGenre());
        return new BookDto(book.getId(), book.getTitle(), authorDto, genreDto);
    }

    @Override
    public BookUpdateDto toDto(BookDto book) {
        return new BookUpdateDto(book.id(), book.title(), book.authorDto().id(), book.genreDto().id());
    }

    @Override
    public Book toModel(BookDto bookDto) {
        Author author = authorMapper.toModel(bookDto.authorDto());
        Genre genre = genreMapper.toModel(bookDto.genreDto());
        return new Book(bookDto.id(), bookDto.title(), author, genre);
    }

    @Override
    public Book toModel(BookCreateDto bookDto) {
        return new Book(null, bookDto.title(), null, null);
    }

    @Override
    public Book toModel(BookUpdateDto bookDto) {
        return new Book(bookDto.id(), bookDto.title(), null, null);
    }
}
