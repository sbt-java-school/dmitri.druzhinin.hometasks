package ru.sbt.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.sbt.entities.Recipe;

import java.util.*;

/**
 * Объект этого класса отвечает за получение из БД объектов класса {@code Recipe} и помещение их обратно.
 */
public class RecipeDao {
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_NAME = "select name, description from recipes where name=?";
    private static final String SQL_SELECT_ALL = "select name, description from recipes";
    private static final String SQL_INSERT = "insert into recipes (name, description) values(?, ?)";
    private static final String SQL_DELETE="delete from recipes where name=?";

    public RecipeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Optional<Recipe> findByName(String name) {
        List<Recipe> recipes= jdbcTemplate.query(SQL_SELECT_BY_NAME, new Object[]{name}, (resultSet, rowNum)->
                new Recipe(resultSet.getString(1),resultSet.getString(2)));
        return (recipes.size()==0)? Optional.empty():Optional.of(recipes.get(0));
    }

    /**
     * @return Список всех рецептов.
     */
    public List<Recipe> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, resultSet->{
            List<Recipe> recipes= new ArrayList<>();
            while(resultSet.next()){
                recipes.add(new Recipe(resultSet.getString(1), resultSet.getString(2)));
            }
            return recipes;
        });
    }

    /**
     * Вносит рецепт в базу.
     * @param recipe
     */
    public void create(Recipe recipe) {
        jdbcTemplate.update(SQL_INSERT, recipe.getName(), recipe.getDescription());
    }

    public void deleteByName(String name) {
        jdbcTemplate.update(SQL_DELETE, name);
    }
}
