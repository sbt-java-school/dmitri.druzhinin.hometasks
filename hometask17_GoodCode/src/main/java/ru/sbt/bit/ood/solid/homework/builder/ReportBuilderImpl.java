package ru.sbt.bit.ood.solid.homework.builder;

import ru.sbt.bit.ood.solid.homework.entities.SalaryPayment;

import java.math.BigDecimal;
import java.util.List;

public class ReportBuilderImpl implements ReportBuilder {

    @Override
    public String buildPaymentReport(List<SalaryPayment> results) {
        // create a StringBuilder holding a resulting html
        StringBuilder resultingHtml = new StringBuilder();
        resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
        BigDecimal totals = new BigDecimal(0);
        for (SalaryPayment salaryPayment : results) {
            // process each row of query results
            resultingHtml.append("<tr>"); // add row start tag
            resultingHtml.append("<td>").append(salaryPayment.getEmployeeName()).append("</td>"); // appending employee name
            resultingHtml.append("<td>").append(salaryPayment.getSalary()).append("</td>"); // appending employee salary for period
            resultingHtml.append("</tr>"); // add row end tag
            totals.add(salaryPayment.getSalary()); // add salary to totals
        }
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        resultingHtml.append("</table></body></html>");
        return resultingHtml.toString();
    }

}
