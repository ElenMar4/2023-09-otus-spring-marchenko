package ru.otus.marchenko.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.marchenko.events.MongoGenreCascadeDeleteEventsListener;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({MongoGenreCascadeDeleteEventsListener.class})
@DisplayName("GenreRepository должен ")
class GenreRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @DisplayName("при удалении жанра должен удалить запись в книге")
    @Test
    void shouldUpdateBookFromRemoveGenre() {
        List<Genre> genres = genreRepository.findAll();
        Genre genre = genres.get(0);

        List<Book> books = bookRepository.findBooksByGenres(genre.getId());

        for (Book book : books) {
            List<Genre> genresByBook = bookRepository.findById(book.getId()).get().getGenres();
            assertThat(genresByBook.contains(genre)).isEqualTo(true);
        }
        genreRepository.deleteById(genre.getId());

        for (Book book : books) {
            List<Genre> genresByBook = bookRepository.findById(book.getId()).get().getGenres();
            assertThat(genresByBook.contains(genre)).isEqualTo(false);
        }
    }
}