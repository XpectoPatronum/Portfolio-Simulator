package com.project.commons.repository;

import com.project.commons.model.User;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Optional;

@Singleton
public class UserRepository{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    @Inject
    DataSource dataSource;

    public User save(User user){
        Jdbi jdbi = Jdbi.create(dataSource);
        try (Handle handle = jdbi.open()) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?);";
            handle.createUpdate(query)
                    .bind(0, user.getUsername())
                    .bind(1, user.getPassword())
                    .execute();
            return user;
        }
        catch (JdbiException e){
            LOGGER.error("Exception Occurred ",e);
            throw new RuntimeException();
        }
    }

    public boolean existsByUsername(String username) {
        Jdbi jdbi = Jdbi.create(dataSource);
        try (Handle handle = jdbi.open()) {
            String query = "SELECT EXISTS(SELECT 1 FROM users WHERE username = ?)";
            return handle.createQuery(query)
                    .bind(0, username)
                    .mapTo(Boolean.class)
                    .one();
        }
    }

    public Optional<User> findByUsername(String username) {
        Jdbi jdbi = Jdbi.create(dataSource);
        try (Handle handle = jdbi.open()) {
            String query = "SELECT id, username, password FROM users WHERE username = ?";
            return handle.createQuery(query)
                    .bind(0, username)
                    .map((rs, ctx) -> new User(
                            rs.getLong("id"),
                            rs.getString("username"),
                            rs.getString("password")
                    ))
                    .findFirst();
        }
    }
}