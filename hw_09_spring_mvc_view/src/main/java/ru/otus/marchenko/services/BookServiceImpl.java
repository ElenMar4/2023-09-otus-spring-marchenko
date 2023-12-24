package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.dto.book.BookDto;
import ru.otus.marchenko.dto.book.BookUpdateDto;
import ru.otus.marchenko.dto.book.BookCreateDto;
import ru.otus.marchenko.exceptions.NotFoundException;
import ru.otus.marchenko.mappers.BookMapper;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.repositories.AuthorRepository;
import ru.otus.marchenko.repositories.BookRepository;
import ru.otus.marchenko.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->new NotFoundException("Book not found"));
        return bookMapper.toDto(book);
    }

    @Transactional
    @Override
    public void create(BookCreateDto bookDto) {
        Book book = composeBook(bookMapper.toModel(bookDto), bookDto.authorId(), bookDto.genreId());
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void update(BookUpdateDto bookDto) {
        Book book = composeBook(bookMapper.toModel(bookDto), bookDto.authorId(), bookDto.genreId());
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Book composeBook(Book book, Long authorId, Long genreId){
        Author author = authorRepository.findById(authorId)
                .orElseThrow(()->new NotFoundException("Author not found"));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(()->new NotFoundException("Genre not found"));
        book.setAuthor(author);
        book.setGenre(genre);
        return book;
    }
}
