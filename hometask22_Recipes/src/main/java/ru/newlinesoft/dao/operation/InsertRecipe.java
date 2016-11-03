package ru.newlinesoft.dao.operation;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class InsertRecipe extends SqlUpdate {
    private static final String SQL_INSERT_RECIPE = "insert into recipes (name, description) values(:name, :description)";

    public InsertRecipe(DataSource dataSource) {
        super(dataSource, SQL_INSERT_RECIPE);
        declareParameter(new SqlParameter("name", Types.VARCHAR));
        declareParameter(new SqlParameter("description", Types.VARCHAR));
    }
}
