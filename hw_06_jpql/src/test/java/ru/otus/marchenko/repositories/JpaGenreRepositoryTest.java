package ru.otus.marchenko.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.marchenko.models.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import(JpaGenreRepository.class)
class JpaGenreRepositoryTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private JpaGenreRepository genreRepository;

    private static final int EXPECTED_NUMBER_OF_GENRE = 6;
    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final long FIRST_GENRE_ID = 1L;
    private static final String FIRST_GENRE_NAME = "Genre_1";
    private static final long SECOND_GENRE_ID = 2L;
    private static final String NEW_GENRE_NAME = "Genre_new";

    @DisplayName("должен загружать список всех жанров с полной информацией о них")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        val genres = genreRepository.findAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRE)
                .allMatch(g -> g.getName() != null);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName(" должен загружать информацию о нужном жанре по его id")
    @Test
    void shouldFindExpectedGenreById() {
        Optional<Genre> optionalActualGenre = genreRepository.findById(FIRST_GENRE_ID);
        Genre expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName(" должен загружать список жанров с полной информацией по списку id")
    @Test
    void shouldReturnCorrectGenresListWithAllInfoByIds() {
        List<Genre> actualGenres = List.of(
                em.find(Genre.class, FIRST_GENRE_ID),
                em.find(Genre.class, SECOND_GENRE_ID));
        Set<Long> ids = Set.of(FIRST_GENRE_ID, SECOND_GENRE_ID);
        List<Genre> expectedGenres = genreRepository.findAllByIds(ids);
        assertThat(actualGenres).isEqualTo(expectedGenres);
    }

    @DisplayName(" должен загружать информацию о нужном жанре по его имени")
    @Test
    void shouldFindExpectedGenreByName() {
        val actualGenre = genreRepository.findByName(FIRST_GENRE_NAME);
        val expectedGenre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        assertThat(actualGenre).isPresent()
                .get().usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @DisplayName(" должен корректно сохранять всю информацию о жанре")
    @Test
    void shouldSaveAllGenreInfo() {
        Genre genre = new Genre(null, NEW_GENRE_NAME);
        genreRepository.save(genre);
        assertThat(genre.getId()).isNotNull();
        Genre actualGenre = em.find(Genre.class, genre.getId());
        assertThat(actualGenre).isNotNull().matches(g -> g.getName() != null);
    }

    @DisplayName(" должен удалять заданный жанр по его id")
    @Test
    void shouldDeleteGenreNameById() {
        Genre genre = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(genre).isNotNull();
        em.detach(genre);
        genreRepository.deleteById(FIRST_GENRE_ID);
        Genre deletedGenres = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(deletedGenres).isNull();
    }
}