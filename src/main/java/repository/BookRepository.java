package repository;

import library.Book;
import enums.BookAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("""
                SELECT BookID, Title, ISBN, AuthorID, GenreID, rating, AccessID, LanguageID
                FROM book""", BeanPropertyRowMapper.newInstance(Book.class));
    }

    public Book findBookById(long bookID) {
        return jdbcTemplate.queryForObject("""
                SELECT BookID, Title, ISBN, AuthorID, GenreID, rating, AccessID, LanguageID
                FROM book WHERE BookID = :bookID""", new MapSqlParameterSource("bookID", bookID), BeanPropertyRowMapper.newInstance(Book.class));
    }

    public Long findBookByNameAndAuthor(String bookName, long authorID) {
        return jdbcTemplate.queryForObject("""
                        SELECT BookID
                        FROM book WHERE Title = :bookName AND AuthorID = :authorID""",
                new MapSqlParameterSource("bookName", bookName).addValue("authorID", authorID),
                Long.class);
    }

    public List<Book> findBookByTitle(String bookTitle) {
        return jdbcTemplate.query("""
                SELECT BookID, Title, ISBN, AuthorID, GenreID, rating, AccessID, LanguageID
                FROM book WHERE Title LIKE :bookTitle""", new MapSqlParameterSource("bookTitle", bookTitle), BeanPropertyRowMapper.newInstance(Book.class));
    }

    public List<Book> findBookByAuthor(long authorID) {
        return jdbcTemplate.query("""
                SELECT BookID, Title, ISBN, AuthorID, GenreID, rating, AccessID, LanguageID
                FROM book WHERE AuthorID = :authorID""", new MapSqlParameterSource("authorID", authorID), BeanPropertyRowMapper.newInstance(Book.class));
    }

    public String getGenreByID(long genreID) {
        return jdbcTemplate.queryForObject("""
                SELECT genreName
                FROM genre
                WHERE genreID = :genreID""", new MapSqlParameterSource("genreID", genreID), String.class);
    }

    public Long getGenreID(String genreName) {
        return jdbcTemplate.queryForObject("""
                SELECT genreID
                FROM genre
                WHERE genreName = :genreName""", new MapSqlParameterSource("genreName", genreName), Long.class);
    }

    public String getLanguageById(long languageID) {
        return jdbcTemplate.queryForObject("""
                 SELECT languageName
                 FROM language
                 WHERE languageID = :languageID""", new MapSqlParameterSource("languageID", languageID), String.class);
    }

    public Long getLanguageID(String languageName) {
        return jdbcTemplate.queryForObject("""
                SELECT languageID
                FROM language
                WHERE languageName = :languageName""", new MapSqlParameterSource("languageName", languageName), Long.class);
    }

    public Double getBookRating(long bookID) {
        return jdbcTemplate.queryForObject("""
                SELECT rating
                FROM book
                WHERE BookID = :bookID""", new MapSqlParameterSource("bookID", bookID), Double.class);
    }

    public Integer getBookIDCount(long bookID) {
        return jdbcTemplate.queryForObject("""
                SELECT COUNT(BookID) AS countBooks
                FROM book
                WHERE BookID = :bookID""", new MapSqlParameterSource("bookID", bookID), Integer.class);
    }

    public Integer countBookAppearances(long bookID) {
        return jdbcTemplate.queryForObject("""
                SELECT COUNT(*) AS appearances
                FROM bookLibrary
                WHERE BookID = :bookID""", new MapSqlParameterSource("bookID", bookID), Integer.class);
    }

    public void createBook(String title, String ISBN, long authorID, long genreID, double rating, long accessID, long languageID) {
        jdbcTemplate.update("""
                INSERT INTO book (Title, ISBN, AuthorID, GenreID, rating, AccessID, LanguageID)
                VALUES(:title, :ISBN, :authorID, :genreID, :rating, :accessID, :languageID)""",
                new MapSqlParameterSource("title", title)
                        .addValue("ISBN", ISBN)
                        .addValue("authorID", authorID)
                        .addValue("genreID", genreID)
                        .addValue("rating", rating)
                        .addValue("accessID", accessID)
                        .addValue("languageID", languageID));
    }

    public void updateBookRating(long bookID, double rating) {
        jdbcTemplate.update("""
                UPDATE book
                SET rating = :rating
                WHERE BookID = :bookID""",
                new MapSqlParameterSource("bookID", bookID)
                        .addValue("rating", rating));
    }

    public void updateBookAccess(long bookID, long accessID) {
        jdbcTemplate.update("""
                UPDATE book
                SET AccessID = :accessID
                WHERE BookID = :bookID""",
                new MapSqlParameterSource("bookID", bookID)
                        .addValue("accessID", accessID));
    }
}
