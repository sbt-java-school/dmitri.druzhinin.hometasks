package ru.sbt.bit.ood.solid.homework.entities;

import java.math.BigDecimal;

public class SalaryPayment {

    private final Long employeeId;
    private final String employeeName;
    private final BigDecimal salary;

    public SalaryPayment(Long employeeId, String employeeName, BigDecimal salary) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.salary = salary;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

}
