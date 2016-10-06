package ru.sbt.bit.ood.solid.homework.builder;

import ru.sbt.bit.ood.solid.homework.entities.SalaryPayment;

import java.util.List;

public interface ReportBuilder {
    String buildPaymentReport(List<SalaryPayment> results);
}
