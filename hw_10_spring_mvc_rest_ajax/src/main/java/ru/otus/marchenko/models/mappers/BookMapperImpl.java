package ru.otus.marchenko.models.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.author.AuthorDto;
import ru.otus.marchenko.models.dto.book.BookCreateDto;
import ru.otus.marchenko.models.dto.book.BookDto;
import ru.otus.marchenko.models.dto.book.BookUpdateDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;

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
    public Book toModel(BookDto bookDto) {
        Author author = authorMapper.toModel(bookDto.authorDto());
        Genre genre = genreMapper.toModel(bookDto.genreDto());
        return new Book(bookDto.id(), bookDto.title(), author, genre);
    }

    @Override
    public Book toModel(BookCreateDto bookDto, Author author, Genre genre) {
        return new Book(null, bookDto.title(), author, genre);
    }

    @Override
    public Book toModel(BookUpdateDto bookDto, Book book, Author author, Genre genre) {
        book.setTitle(bookDto.title());
        book.setAuthor(author);
        book.setGenre(genre);
        return book;
    }
}
