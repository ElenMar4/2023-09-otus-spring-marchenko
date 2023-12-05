package ru.otus.marchenko.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import({JpaBookRepository.class, JpaGenreRepository.class, JpaAuthorRepository.class})
class JpaBookRepositoryTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private JpaBookRepository bookRepository;
    @Autowired
    private JpaGenreRepository genreRepository;
    @Autowired
    private JpaAuthorRepository authorRepository;

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;
    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final long FIRST_BOOK_ID = 1L;
    private static final String NEW_BOOK_TITLE = "Book_new";
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long FIRST_GENRE_ID = 1L;
    private static final long SECOND_GENRE_ID = 2L;
    private static final String UPDATE_BOOK_TITLE = "Book_update";

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectBookListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        val books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> b.getTitle() != null)
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getGenres() != null);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName(" должен загружать информацию о нужной книге по его id")
    @Test
    void shouldFindExpectedBookById() {
        Optional<Book> optionalActualBook = bookRepository.findById(FIRST_BOOK_ID);
        Book expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName(" должен корректно сохранять всю информацию о книге")
    @Test
    void shouldSaveAllBookInfo() {
        Author author = authorRepository.findById(FIRST_AUTHOR_ID).get();
        List<Genre> genres = genreRepository.findAllByIds(Set.of(FIRST_GENRE_ID, SECOND_GENRE_ID));
        Book book = new Book(null, NEW_BOOK_TITLE, author, genres);
        bookRepository.save(book);
        assertThat(book.getId()).isNotNull();
        Book actualBook = em.find(Book.class, book.getId());
        assertThat(actualBook).isNotNull()
                .matches(b -> b.getAuthor().equals(author))
                .matches(b -> b.getTitle().equals(NEW_BOOK_TITLE))
                .matches(b -> b.getGenres().size() == genres.size());
    }

    @DisplayName(" должен изменять информацию у заданной книги по id")
    @Test
    void shouldUpdateBookById() {
        Book firstBook = em.find(Book.class, FIRST_BOOK_ID);
        String oldTitle = firstBook.getTitle();
        em.detach(firstBook);
        Author author = authorRepository.findById(FIRST_AUTHOR_ID).get();
        List<Genre> genres = List.of(genreRepository.findById(FIRST_GENRE_ID).get());
        Book newBook = new Book(FIRST_BOOK_ID, UPDATE_BOOK_TITLE, author, genres);
        bookRepository.save(newBook);
        Book updatedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(updatedBook.getTitle()).isNotEqualTo(oldTitle).isEqualTo(UPDATE_BOOK_TITLE);
    }

    @DisplayName(" должен удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(book).isNotNull();
        em.detach(book);
        bookRepository.deleteById(FIRST_BOOK_ID);
        Book deletedBooks = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(deletedBooks).isNull();
    }
}