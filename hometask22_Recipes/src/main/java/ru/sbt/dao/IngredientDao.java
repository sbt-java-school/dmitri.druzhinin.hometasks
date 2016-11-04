package ru.sbt.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.BatchSqlUpdate;
import ru.sbt.entities.Ingredient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Объект этого класса отвечает за получение из БД объектов класса {@code Ingredient} и помещение их обратно.
 */
public class IngredientDao {
    private JdbcTemplate jdbcTemplate;
    private BatchSqlUpdate insertRecipeIngredients;
    private static final String SQL_SELECT_ALL="select name, measure_unit from ingredients";
    private static final String SQL_INSERT="insert into ingredients (name, measure_unit) values(?, ?)";
    private static final String SQL_DELETE="delete from ingredients where name=?";

    public IngredientDao(JdbcTemplate jdbcTemplate, BatchSqlUpdate insertRecipeIngredients) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertRecipeIngredients=insertRecipeIngredients;
    }

    public List<Ingredient> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, (resultSet, rowNumber) ->
                new Ingredient(resultSet.getString(1), resultSet.getString(2)));
    }

    public void create(Ingredient ingredient) {
        jdbcTemplate.update(SQL_INSERT, ingredient.getName(), ingredient.getMeasureUnit());
    }

    public int deleteByName(String name) {
        return jdbcTemplate.update(SQL_DELETE, name);
    }

    /**
     * Добавляет ингредиенты и их количества определенному рецепту
     * @param recipeName название рецепта, которому добавляются ингредиенты
     * @param ingredients добавляемые ингредиенты
     */
    private void addIngredientsByRecipeName(String recipeName, Map<Ingredient, Integer> ingredients) {
        for (Map.Entry<Ingredient, Integer> ingredientEntry : ingredients.entrySet()) {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("recipe_name", recipeName);
            parameterMap.put("ingredient_name", ingredientEntry.getKey().getName());
            parameterMap.put("amount", ingredientEntry.getValue());
            insertRecipeIngredients.updateByNamedParam(parameterMap);
        }
        insertRecipeIngredients.flush();
    }
}
