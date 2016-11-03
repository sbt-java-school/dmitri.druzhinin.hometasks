package ru.newlinesoft.dao.operation;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class InsertIngredient extends SqlUpdate {
    private static final String SQL_INSERT_INGREDIENT = "insert into ingredients (name, measure_unit) values(:name, :measure_unit)";

    public InsertIngredient(DataSource dataSource) {
        super(dataSource, SQL_INSERT_INGREDIENT);
        declareParameter(new SqlParameter("name", Types.VARCHAR));
        declareParameter(new SqlParameter("measure_unit", Types.VARCHAR));
    }
}
