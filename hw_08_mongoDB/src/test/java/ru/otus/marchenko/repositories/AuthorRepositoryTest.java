package ru.otus.marchenko.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.DisplayName;
import org.springframework.context.annotation.Import;
import ru.otus.marchenko.events.MongoAuthorCascadeDeleteEventsListener;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({MongoAuthorCascadeDeleteEventsListener.class})
@DisplayName("AuthorRepository должен ")
class AuthorRepositoryTest extends AbstractRepositoryTest{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @DisplayName("при удалении автора должен удалить запись в книге")
    @Test
    void shouldUpdateBookFromRemoveAuthor() {
        List<Author> authors = authorRepository.findAll();
        String authorId = authors.get(0).getId();

        List<Book> books = bookRepository.findBooksByAuthorId(authorId);
        assertThat(books).isNotEmpty();

        authorRepository.deleteById(authorId);

        for (Book book : books) {

            assertThat(bookRepository.findById(book.getId()).get().getAuthor()).isNull();
        }
    }
}