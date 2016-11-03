package ru.sbt.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.dao.operation.InsertIngredient;
import ru.sbt.entities.Ingredient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Объект этого класса отвечает за получение из БД объектов класса {@code Ingredient} и помещение их обратно.
 */
public class IngredientDao {
    private JdbcTemplate jdbcTemplate;
    private SqlUpdate insertIngredient;

    public IngredientDao(JdbcTemplate jdbcTemplate, InsertIngredient insertIngredient) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertIngredient = insertIngredient;
    }

    @Transactional(readOnly = true)
    public List<Ingredient> findAll() {
        String sql = "select name, measure_unit from ingredients";
        List<Ingredient> ingredients = jdbcTemplate.query(sql, (resultSet, rowNumber) -> new Ingredient(resultSet.getString(1), resultSet.getString(2)));
        return ingredients;
    }

    @Transactional
    public void create(Ingredient ingredient) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("name", ingredient.getName());
        parameterMap.put("measure_unit", ingredient.getMeasureUnit());
        insertIngredient.updateByNamedParam(parameterMap);
    }

    @Transactional
    public int deleteByName(String ingredientName) {
        return jdbcTemplate.update("delete from ingredients where name=?", preparedStatemenent -> preparedStatemenent.setString(1, ingredientName));
    }
}
