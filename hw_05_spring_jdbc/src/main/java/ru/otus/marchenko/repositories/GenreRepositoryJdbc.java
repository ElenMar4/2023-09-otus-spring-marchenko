package ru.otus.marchenko.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.marchenko.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreRepositoryJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Genre> findAll() {
        return namedParameterJdbcOperations.query("select id, name from genres", new GnreRowMapper());
    }

    @Override
    public List<Genre> findAllByIds(List<Long> ids) {
        SqlParameterSource params = new MapSqlParameterSource("ids", ids);
        return namedParameterJdbcOperations.query("select id, name from genres where id in (:ids)",
                params,
                new GnreRowMapper()
        );
    }

    private static class GnreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
