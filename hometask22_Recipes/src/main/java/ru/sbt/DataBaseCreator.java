package ru.sbt;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
public class DataBaseCreator {
    private JdbcTemplate jdbcTemplate;

    public DataBaseCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("app-context-xml.xml");
        context.getBean(DataBaseCreator.class).executeScript("hometask22_Recipes/src/main/sql/create_table.sql");
    }

    public void executeScript(String fileName) {
        try {
            String sqlScript = FileUtils.readFileToString(new File(fileName));
            executeSql(sqlScript);
            System.out.println(sqlScript);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not read file " + fileName);
        }
    }

    private void executeSql(String sqlScript){
        jdbcTemplate.execute(sqlScript);
    }
}
