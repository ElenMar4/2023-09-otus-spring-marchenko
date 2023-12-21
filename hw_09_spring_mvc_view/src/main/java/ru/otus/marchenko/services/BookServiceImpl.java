package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.dto.book.BookDto;
import ru.otus.marchenko.dto.book.BookUpdateDto;
import ru.otus.marchenko.dto.book.BookCreateDto;
import ru.otus.marchenko.exceptions.EntityNotFoundException;
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

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(BookDto::new).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(long id) {
        return bookRepository.findById(id).map(BookDto::new).orElseThrow(() -> new EntityNotFoundException("book not found"));
    }

    @Transactional
    @Override
    public void insert(BookCreateDto bookDto) {
        Author author = authorRepository.findByFullName(bookDto.authorName())
                .orElseThrow(EntityNotFoundException::new);
        Genre genre = genreRepository.findByName(bookDto.genreName())
                .orElseThrow(EntityNotFoundException::new);
        bookRepository.save(new Book(null, bookDto.title(), author, genre));
    }

    @Transactional
    @Override
    public void update(BookUpdateDto bookDto) {
        Book book = bookRepository.findById(bookDto.id())
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookDto.id())));
        if (!bookDto.authorName().isEmpty()) {
            Author author = authorRepository.findByFullName(bookDto.authorName())
                    .orElseThrow(() -> new EntityNotFoundException("Author with name %s not found".formatted(bookDto.authorName())));
            book.setAuthor(author);
        }
        if (!bookDto.genreName().isEmpty()) {
            Genre genre = genreRepository.findByName(bookDto.genreName())
                    .orElseThrow(() -> new EntityNotFoundException("Genre with name %s not found".formatted(bookDto.genreName())));
            book.setGenre(genre);
        }
        if (!bookDto.title().isEmpty()) {
            book.setTitle(bookDto.title());
        }
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
