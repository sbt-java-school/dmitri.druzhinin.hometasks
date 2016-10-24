package ru.sbt.school;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс, нужный для создания базы данных и таблицы.
 */
public class DataBaseUpdater {
    private String JDBC_URL = "jdbc:mysql://localhost:3306/";//на реальном проекте эти данные в полях не хранятся.
    private String USER_NAME = "coder";
    private String PASSWORD = "12";

    public static void main(String[] args) {
        new DataBaseUpdater().executeScript("hometask21_PersistentCache/src/main/sql/invokes.sql");
    }

    public void executeScript(String fileName) {
        try {
            String sqlScript = FileUtils.readFileToString(new File(fileName));
            executeSql(sqlScript);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not read file " + fileName);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Server unreachable or bad script " + fileName);
        }
    }

    private void executeSql(String sqlScript) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            boolean execute = statement.execute(sqlScript);
            System.out.println("---------------------------------");
            System.out.println("State:" + execute);
            System.out.println("SQL: " + sqlScript);
            System.out.println("---------------------------------");
        }
    }
}
