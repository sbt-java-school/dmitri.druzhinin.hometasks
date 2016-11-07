package ru.sbt;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DataBaseCreator {
    private static Logger logger = LoggerFactory.getLogger(DataBaseCreator.class);
    private JdbcTemplate jdbcTemplate;

    public DataBaseCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void createDatabase() {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context-xml-database-creator.xml");
        context.getBean(DataBaseCreator.class).executeScript("/createTable.sql");
    }

    public void executeScript(String fileName) {
        try {
            URL sqlResource = DataBaseCreator.class.getResource(fileName);
            String sqlScript = FileUtils.readFileToString(new File(sqlResource.getFile()));
            jdbcTemplate.execute(sqlScript);
            logger.info("The database is created");
        } catch (IOException e) {
            logger.error("Sorry. Problems creating database. Can't read file " + fileName);
            throw new IllegalArgumentException("Can not read file " + fileName);
        }
    }
}
