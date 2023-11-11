package ru.otus.marchenko.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbc implements BookRepository {

    private final GenreRepository genreRepository;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        Book result;
        try {
            result = namedParameterJdbcOperations.queryForObject(
                    "select books.id, books.title, books.author_id, authors.full_name " +
                            "from books join authors on books.author_id = authors.id " +
                            "where books.id = :id",
                    Map.of("id", id),
                    new BookRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
        var relations = getAllGenreRelations();
        Map<Long, List<Long>> relationsMap = relations.stream().collect(groupingBy(BookGenreRelation::bookId,
                mapping(BookGenreRelation::genreId, toList())));
        var genresById = genreRepository.findAllByIds(relationsMap.get(id));
        result.setGenres(genresById);
        return Optional.of(result);
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        removeGenresRelationsFor(id);
        namedParameterJdbcOperations.update("delete from books where id = :id", Map.of("id", id));
        var x = findById(id);

        var t = 0;
    }

    private List<Book> getAllBooksWithoutGenres() {
        return namedParameterJdbcOperations.query(
                "select books.id, books.title, books.author_id, authors.full_name " +
                        "from books join authors on books.author_id = authors.id",
                new BookResultSetExtractor());
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return namedParameterJdbcOperations.query("select book_id, genre_id from books_genres",
                (rs, rowNum) -> new BookGenreRelation(rs.getLong("book_id"), rs.getLong("genre_id")));
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        Map<Long, List<Long>> relationsMap = relations.stream().collect(groupingBy(BookGenreRelation::bookId,
                mapping(BookGenreRelation::genreId, toList())));
        Map<Long, Book> bookMap = booksWithoutGenres.stream().collect(Collectors.toMap(Book::getId, Function.identity()));
        Map<Long, Genre> genreMap = genres.stream().collect(Collectors.toMap(Genre::getId, Function.identity()));

        for (Map.Entry<Long, List<Long>> entry : relationsMap.entrySet()) {
            List<Genre> genresOfBook = new ArrayList<>();
            for (Long value : entry.getValue()) {
                genresOfBook.add(genreMap.get(value));
            }
            bookMap.get(entry.getKey()).setGenres(genresOfBook);
        }
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource(Map.of(
                "title", book.getTitle(),
                "author_id", book.getAuthor().getId()
        ));
        namedParameterJdbcOperations.update("insert into books (title, author_id) values (:title, :author_id)",
                params, keyHolder, new String[]{"id"});
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        SqlParameterSource params = new MapSqlParameterSource(Map.of(
                "id", book.getId(),
                "title", book.getTitle(),
                "author_id", book.getAuthor().getId()
        ));
        namedParameterJdbcOperations.update("update books set title = :title, author_id = :author_id where id = :id; ",
                params);
        removeGenresRelationsFor(book.getId());
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        for (Genre genre : book.getGenres()) {
            namedParameterJdbcOperations.update("insert into books_genres (book_id, genre_id) values (:book_id, :genre_id)",
                    Map.of("book_id", book.getId(), "genre_id", genre.getId()));
        }
    }

    private void removeGenresRelationsFor(Long book_id) {
        namedParameterJdbcOperations.update("delete from books_genres where book_id = :id", Map.of("id", book_id));
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("books.id");
            String title = rs.getString("books.title");
            Author author = new Author(rs.getLong("books.author_id"), rs.getString("authors.full_name"));
            return new Book(id, title, author, null);
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<List<Book>> {

        @Override
        public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Book> listBook = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong("books.id");
                String title = rs.getString("books.title");
                Author author = new Author(rs.getLong("books.author_id"), rs.getString("authors.full_name"));
                listBook.add(new Book(id, title, author, null));
            }
            return listBook;
        }
    }


    private record BookGenreRelation(long bookId, long genreId) {
    }
}
