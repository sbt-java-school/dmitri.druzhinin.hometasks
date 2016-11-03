package ru.newlinesoft.dao.operation;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class InsertRecipeIngredients extends BatchSqlUpdate {
    private static final String SQL_INSERT_RECIPE_INGREDIENTS = "insert into ingredients_of_recipes (rname, iname, amount) " +
            "values(:recipe_name, :ingredient_name, :amount)";
    private static final int BATCH_SIZE = 10;

    public InsertRecipeIngredients(DataSource dataSource) {
        super(dataSource, SQL_INSERT_RECIPE_INGREDIENTS);
        declareParameter(new SqlParameter(("recipe_name"), Types.VARCHAR));
        declareParameter(new SqlParameter(("ingredient_name"), Types.VARCHAR));
        declareParameter(new SqlParameter(("amount"), Types.INTEGER));

        setBatchSize(BATCH_SIZE);
    }
}
