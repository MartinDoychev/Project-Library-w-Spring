package repository;

import library.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class LibraryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public LibraryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Library> getAllLibraries() {
        String query = "SELECT * FROM library";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Library.class));
    }

    public Library getLibraryByID(long libraryID) {
        String query = "SELECT * FROM library WHERE libraryID = :libraryID";
        MapSqlParameterSource params = new MapSqlParameterSource("libraryID", libraryID);
        return jdbcTemplate.queryForObject(query, params, new BeanPropertyRowMapper<>(Library.class));
    }

    public void createLibrary(Library library) {
        String query = "INSERT INTO library (libraryName) VALUES (:libraryName)";
        MapSqlParameterSource params = new MapSqlParameterSource("libraryName", library.getLibraryName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, params, keyHolder);
        library.setLibraryID(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    public void updateLibrary(Library library) {
        String query = "UPDATE library SET libraryName = :libraryName WHERE libraryID = :libraryID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("libraryName", library.getLibraryName())
                .addValue("libraryID", library.getLibraryID());
        jdbcTemplate.update(query, params);
    }

    public void deleteLibrary(long libraryID) {
        String query = "DELETE FROM library WHERE libraryID = :libraryID";
        MapSqlParameterSource params = new MapSqlParameterSource("libraryID", libraryID);
        jdbcTemplate.update(query, params);
    }
}
