package ru.sbt.bit.ood.solid.homework.dao;

import ru.sbt.bit.ood.solid.homework.entities.SalaryPayment;

import java.time.LocalDate;
import java.util.List;

public interface SalaryPaymentDao {
    List<SalaryPayment> salaryPaymentGetByParams(String departmentId, LocalDate dateFrom, LocalDate dateTo);
}
