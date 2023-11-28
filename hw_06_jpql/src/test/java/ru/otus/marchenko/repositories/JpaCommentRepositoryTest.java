package ru.otus.marchenko.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import({JpaCommentRepository.class, JpaBookRepository.class})
class JpaCommentRepositoryTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private JpaCommentRepository commentRepository;
    @Autowired
    private JpaBookRepository bookRepository;

    private static final long FIRST_COMMENT_ID = 1L;
    private static final long SECOND_COMMENT_ID = 2L;
    private static final long THIRD_COMMENT_ID = 3L;
    private static final long BOOK_ID = 1L;
    private static final String NEW_COMMENT_MESSAGE = "Comment_new";

    @DisplayName(" должен загружать информацию о нужном комментарии по его id")
    @Test
    void shouldFindExpectedCommentById() {
        Optional<Comment> optionalActualComment = commentRepository.findById(FIRST_COMMENT_ID);
        Comment expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName(" должен загружать список комментариев с полной информацией по id книги ")
    @Test
    void shouldReturnCorrectCommentListWithAllInfoByBookId() {
        List<Comment> actualComments = List.of(
                em.find(Comment.class, FIRST_COMMENT_ID),
                em.find(Comment.class, SECOND_COMMENT_ID),
                em.find(Comment.class, THIRD_COMMENT_ID));
        List<Comment> expectedComments = commentRepository.findAllByBookId(BOOK_ID);
        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @DisplayName(" должен корректно сохранять всю информацию о комментарии")
    @Test
    void shouldSaveAllCommentInfo() {
        Book book = bookRepository.findById(BOOK_ID).get();
        Comment comment = new Comment(null, NEW_COMMENT_MESSAGE, book);
        commentRepository.save(comment);
        assertThat(comment.getId()).isNotNull();
        Comment actualComment = em.find(Comment.class, comment.getId());
        assertThat(actualComment).isNotNull()
                .matches(c -> c.getMessage().equals(NEW_COMMENT_MESSAGE))
                .matches(c -> c.getBook().equals(book));
    }

    @DisplayName(" должен удалять заданный комментарий по его id")
    @Test
    void shouldDeleteCommentNameById() {
        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(comment).isNotNull();
        em.detach(comment);
        commentRepository.deleteById(FIRST_COMMENT_ID);
        Comment deletedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(deletedComment).isNull();
    }
}