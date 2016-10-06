package ru.sbt.bit.ood.solid.homework.dao;

import ru.sbt.bit.ood.solid.homework.entities.SalaryPayment;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SalaryPaymentDaoImpl implements SalaryPaymentDao {

    private Connection connection;

    public SalaryPaymentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<SalaryPayment> salaryPaymentGetByParams(String departmentId, LocalDate dateFrom, LocalDate dateTo) {
        List<SalaryPayment> salaryPayments = new ArrayList<>();
        try {
            // prepare statement with sql
            PreparedStatement ps = connection.prepareStatement("select emp.id as emp_id, emp.name as emp_name, sum(salary) as salary from employee emp left join " +
                    "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and " +
                    "sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
            // inject parameters to sql
            ps.setString(0, departmentId); // почему здесь не setLong() ???
            ps.setDate(1, new Date(dateFrom.toEpochDay()));
            ps.setDate(2, new Date(dateTo.toEpochDay()));
            // execute query and get the results
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Long employeeId = resultSet.getLong("emp_id");
                String employeeName = resultSet.getString("emp_name");
                double salary = resultSet.getDouble("salary");
                SalaryPayment salaryPayment = new SalaryPayment(employeeId, employeeName, new BigDecimal(salary));
                salaryPayments.add(salaryPayment);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return salaryPayments;
    }

}
