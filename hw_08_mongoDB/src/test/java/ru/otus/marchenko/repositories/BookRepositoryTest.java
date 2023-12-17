package ru.otus.marchenko.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.marchenko.events.MongoBookCommentCascadeDeleteEventsListener;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({MongoBookCommentCascadeDeleteEventsListener.class})
@DisplayName("BookRepository должен ")
class BookRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    CommentRepository commentRepository;

    @DisplayName("при удалении книги должен удалить комментарии")
    @Test
    void shouldUpdateBookFromRemoveAuthor() {
        List<Book> books = bookRepository.findAll();
        Book expectBook = books.get(0);
        List<Comment> comments = commentRepository.findAllByBook(expectBook.getId());

        assertThat(comments).isNotEmpty();

        bookRepository.deleteById(expectBook.getId());

        for (Comment comment : comments) {
            assertThat(commentRepository.findById(comment.getId())).isEmpty();
        }
    }
}