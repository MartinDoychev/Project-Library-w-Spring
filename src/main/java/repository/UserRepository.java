package repository;

import library.User;
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
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserById(long userId) {
        String query = "SELECT * FROM user WHERE id = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.queryForObject(query, params, new BeanPropertyRowMapper<>(User.class));
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM user WHERE userName = :username";
        MapSqlParameterSource params = new MapSqlParameterSource("username", username);
        return jdbcTemplate.queryForObject(query, params, new BeanPropertyRowMapper<>(User.class));
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM user";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(User.class));
    }
    public User getAuthorByID(long authorID) {
        String query = "SELECT * FROM user WHERE userID = :authorID";
        MapSqlParameterSource params = new MapSqlParameterSource("authorID", authorID);
        return jdbcTemplate.queryForObject(query, params, new BeanPropertyRowMapper<>(User.class));
    }

    public long getAuthorID(String authorName) {
        String query = "SELECT userID FROM user WHERE CONCAT(firstName, ' ', lastName) = :authorName";
        MapSqlParameterSource params = new MapSqlParameterSource("authorName", authorName);
        return jdbcTemplate.queryForObject(query, params, Long.class);
    }

    public void createUser(User user) {
        String query = "INSERT INTO user (firstName, lastName, email, phoneNumber, isLocked, role, userName, password) " +
                "VALUES (:firstName, :lastName, :email, :phoneNumber, :isLocked, :role, :userName, :password)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("phoneNumber", user.getPhoneNumber())
                .addValue("isLocked", user.isLocked())
                .addValue("role", user.getRole().toString())
                .addValue("userName", user.getUserName())
                .addValue("password", user.getPassword());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, params, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    public void updateUser(User user) {
        String query = "UPDATE user SET firstName = :firstName, lastName = :lastName, email = :email, phoneNumber = :phoneNumber, " +
                "isLocked = :isLocked, role = :role, userName = :userName, password = :password WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("phoneNumber", user.getPhoneNumber())
                .addValue("isLocked", user.isLocked())
                .addValue("role", user.getRole().toString())
                .addValue("userName", user.getUserName())
                .addValue("password", user.getPassword())
                .addValue("id", user.getId());

        jdbcTemplate.update(query, params);
    }

    public void deleteUser(long userId) {
        String query = "DELETE FROM user WHERE id = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        jdbcTemplate.update(query, params);
    }
}
