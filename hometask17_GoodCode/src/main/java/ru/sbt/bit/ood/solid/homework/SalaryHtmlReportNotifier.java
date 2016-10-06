package ru.sbt.bit.ood.solid.homework;



import ru.sbt.bit.ood.solid.homework.builder.ReportBuilder;
import ru.sbt.bit.ood.solid.homework.dao.SalaryPaymentDao;
import ru.sbt.bit.ood.solid.homework.entities.SalaryPayment;
import ru.sbt.bit.ood.solid.homework.sender.Sender;

import java.time.LocalDate;
import java.util.List;

public class SalaryHtmlReportNotifier {

    private SalaryPaymentDao salaryPaymentDao;
    private ReportBuilder reportBuilder;
    private Sender sender;

    public SalaryHtmlReportNotifier(SalaryPaymentDao salaryPaymentDao, ReportBuilder reportBuilder, Sender sender) {
        this.salaryPaymentDao = salaryPaymentDao;
        this.reportBuilder = reportBuilder;
        this.sender = sender;
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {
        List<SalaryPayment> salaryPayments = salaryPaymentDao.salaryPaymentGetByParams(departmentId, dateFrom, dateTo);
        String resultingHtml = reportBuilder.buildPaymentReport(salaryPayments);
        sender.send(recipients, resultingHtml);
    }

}
