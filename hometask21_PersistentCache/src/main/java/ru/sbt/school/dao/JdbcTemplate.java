package ru.sbt.school.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTemplate {
    private String JDBC_URL = "jdbc:mysql://localhost:3306/";//на реальном проекте эти данные в полях не хранятся.
    private String USER_NAME = "coderDima";
    private String PASSWORD = "";

    public <T> T execute(JdbcAction<T> action) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD)) {
            return action.execute(connection);
        } catch (SQLException e) {
            throw new IllegalStateException("Error execute", e);
        }
    }

    public interface JdbcAction<T> {
        T execute(Connection connection) throws SQLException;
    }
}
