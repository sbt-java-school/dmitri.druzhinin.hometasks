package ru.sbt.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.dao.operation.InsertRecipe;
import ru.sbt.dao.operation.InsertRecipeIngredients;
import ru.sbt.entities.Ingredient;
import ru.sbt.entities.Recipe;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.*;

/**
 * Объект этого класса отвечает за получение из БД объектов класса {@code Recipe} и помещение их обратно.
 */
public class RecipeDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SqlUpdate insertRecipe;
    private BatchSqlUpdate insertRecipeIngredients;
    private static final String SELECT_SQL = "select recipes.name as recipe_name, description, ingredients.name as ingredient_name, amount, " +
            "measure_unit from recipes, ingredients_of_recipes, ingredients " +
            "where recipes.name=ingredients_of_recipes.rname and ingredients_of_recipes.iname=ingredients.name";

    public RecipeDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        insertRecipe = new InsertRecipe(dataSource);
        insertRecipeIngredients = new InsertRecipeIngredients(dataSource);
    }

    @Transactional(readOnly = true)
    public Optional<Recipe> findByName(String name) {
        return jdbcTemplate.query(connection -> connection.prepareStatement(SELECT_SQL + " and recipes.name=?"),
                prepareStatement -> prepareStatement.setString(1, name), resultSet -> {
                    Map<Ingredient, Integer> ingredients = new HashMap<>();
                    String description = null;
                    while (resultSet.next()) {
                        if (description == null) {
                            description = resultSet.getString("description");
                        }
                        Ingredient ingredient = new Ingredient(resultSet.getString("ingredient_name"), resultSet.getString("measure_unit"));
                        ingredients.put(ingredient, resultSet.getInt("amount"));
                    }
                    return (ingredients.size() == 0) ? Optional.empty() : Optional.of(new Recipe(name, description, ingredients));
                });
    }

    @Transactional(readOnly = true)
    public List<Recipe> findAll() {
        return jdbcTemplate.query(SELECT_SQL, (ResultSet resultSet) -> {
            Map<String, Recipe> recipeByName = new HashMap<>();
            while (resultSet.next()) {
                String recipeName = resultSet.getString("recipe_name");
                Recipe recipe = recipeByName.get(recipeName);
                if (recipe == null) {
                    recipe = new Recipe(recipeName, resultSet.getString("description"), new HashMap<>());
                    recipeByName.put(recipeName, recipe);
                }
                Map<Ingredient, Integer> ingredients = recipe.getIngredients();
                ingredients.put(new Ingredient(resultSet.getString("ingredient_name"), resultSet.getString("measure_unit")), resultSet.getInt("amount"));
            }
            return new ArrayList<>(recipeByName.values());
        });
    }

    @Transactional
    public void create(Recipe recipe) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("name", recipe.getName());
        parameterMap.put("description", recipe.getDescription());
        insertRecipe.updateByNamedParam(parameterMap);
        addIngredientsByRecipeName(recipe.getName(), recipe.getIngredients());
    }

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

    @Transactional
    public void deleteByName(String recipeName) {
        jdbcTemplate.update("delete from ingredients_of_recipes where rname=?",
                preparedStatement -> preparedStatement.setString(1, recipeName));
        jdbcTemplate.update("delete from recipes where name=?", preparedStatement ->
                preparedStatement.setString(1, recipeName));
    }

    @Transactional
    public void update(Recipe recipe) {
        deleteByName(recipe.getName());
        create(recipe);
    }
}
