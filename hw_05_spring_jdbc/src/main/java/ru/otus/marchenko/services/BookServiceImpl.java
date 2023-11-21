package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.marchenko.exceptions.NotFoundException;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.repositories.AuthorRepository;
import ru.otus.marchenko.repositories.BookRepository;
import ru.otus.marchenko.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book insert(String title, long authorId, List<Long> genresIds) {
        return save(null, title, authorId, genresIds);
    }

    @Override
    public Book update(long id, String title, long authorId, List<Long> genresIds) {
        bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(id)));
        return save(id, title, authorId, genresIds);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Book save(Long id, String title, long authorId, List<Long> genresIds) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(authorId)));
        if (genresIds == null){
            throw new NotFoundException("Genres list for book is empty");
        }
        var genres = genreRepository.findAllByIds(genresIds);
        if(genresIds.size() != genres.size()){
            throw new NotFoundException("Not all ids %s found entity".formatted(genresIds));
        }
        var book = new Book(id, title, author, genres);
        return bookRepository.save(book);
    }
}
