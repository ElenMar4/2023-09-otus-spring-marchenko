package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.exceptions.EntityNotFoundException;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.repositories.AuthorRepository;
import ru.otus.marchenko.repositories.BookRepository;
import ru.otus.marchenko.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentService commentService;

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() { return bookRepository.findAll();}

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findById(long id) { return bookRepository.findById(id);}

    @Transactional
    @Override
    public Book insert(String title, Long authorId, Set<Long> genresIds) { return save(null, title, authorId, genresIds);}

    @Transactional
    @Override
    public Book update(Long id, String title, Long authorId, Set<Long> genresIds) {
        bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        return save(id, title, authorId, genresIds);
    }

    @Transactional
    @Override
    public void deleteById(long id) {bookRepository.deleteById(id);}

    private Book save(Long id, String title, Long authorId, Set<Long> genresIds) {

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));

        List<Genre> genres = genreRepository.findAllByIds(genresIds);
        if (isEmpty(genres) || genresIds.size() != genres.size()) {
            throw new EntityNotFoundException("One or all genres with ids %s not found".formatted(genresIds));
        }

        var book = new Book(id, title, author, genres);
        return bookRepository.save(book);
    }

    public List<Comment> findCommentsByBook(long bookId) { return commentService.findAllByBookId(bookId);}
}
