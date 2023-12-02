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

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import(JpaAuthorRepository.class)
class JpaAuthorRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private JpaAuthorRepository authorRepository;

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Author_1";
    private static final String NEW_AUTHOR_FULL_NAME = "Author_new";
    private static final int EXPECTED_NUMBER_OF_AUTHOR = 3;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @DisplayName("должен загружать список всех авторов с полной информацией о них")
    @Test
    void shouldReturnCorrectAuthorsListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        val authors = authorRepository.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHOR)
                .allMatch(a -> a.getFullName() != null);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName(" должен загружать информацию о нужном авторе по его id")
    @Test
    void shouldFindExpectedAuthorById() {
        val actualAuthor = authorRepository.findById(FIRST_AUTHOR_ID);
        val expectedAuthors = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(actualAuthor).isPresent()
                .get().usingRecursiveComparison()
                .isEqualTo(expectedAuthors);
    }

    @DisplayName(" должен загружать информацию о нужном авторе по его имени")
    @Test
    void shouldFindExpectedAuthorByName() {
        val actualAuthor = authorRepository.findByName(FIRST_AUTHOR_NAME);
        val expectedAuthors = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        assertThat(actualAuthor).isPresent()
                .get().usingRecursiveComparison()
                .isEqualTo(expectedAuthors);
    }

    @DisplayName(" должен корректно сохранять всю информацию об авторе")
    @Test
    void shouldSaveAllAuthorInfo() {
        Author author = new Author(null, NEW_AUTHOR_FULL_NAME);
        authorRepository.save(author);
        assertThat(author.getId()).isNotNull();
        Author actualAuthor = em.find(Author.class, author.getId());
        assertThat(actualAuthor).isNotNull().matches(a -> a.getFullName() != null);
    }

    @DisplayName(" должен удалять заданного автора по его id")
    @Test
    void shouldDeleteAuthorNameById() {
        Author author = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(author).isNotNull();
        em.detach(author);
        authorRepository.deleteById(FIRST_AUTHOR_ID);
        Author deletedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(deletedAuthor).isNull();
    }

    @DisplayName(" должен изменять имя у автора по id")
    @Test
    void shouldUpdateAuthorNameById(){
        Author firstAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        String oldName = firstAuthor.getFullName();
        em.detach(firstAuthor);
        firstAuthor.setFullName(NEW_AUTHOR_FULL_NAME);
        authorRepository.save(firstAuthor);
        Author updateAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(updateAuthor.getFullName())
                .isNotEqualTo(oldName)
                .isEqualTo(NEW_AUTHOR_FULL_NAME);
    }
}