package ru.sbt.bit.ood.solid.homework;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.sbt.bit.ood.solid.homework.builder.ReportBuilderImpl;
import ru.sbt.bit.ood.solid.homework.dao.SalaryPaymentDao;
import ru.sbt.bit.ood.solid.homework.dao.SalaryPaymentDaoImpl;
import ru.sbt.bit.ood.solid.homework.sender.SenderImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SalaryHtmlReportNotifier.class)
public class TestSalaryHtmlReportNotifier {

    @Test
    public void test() throws Exception {
        // mock database related stuff
        Connection someFakeConnection = mock(Connection.class);
        ResultSet mockResultSet = getMockedResultSet(someFakeConnection);
        when(mockResultSet.getString("emp_name")).thenReturn("John Doe", "Jane Dow");
        when(mockResultSet.getDouble("salary")).thenReturn(100.0, 100.0, 50.0, 50.0);
        // mock mail related stuff
        MimeMessageHelper mockMimeMessageHelper = getMockedMimeMessageHelper();

        // set up parameters
        SalaryPaymentDao salaryPaymentDao=new SalaryPaymentDaoImpl(someFakeConnection);
        SalaryHtmlReportNotifier notificator = new SalaryHtmlReportNotifier(salaryPaymentDao, new ReportBuilderImpl(), new SenderImpl());
        LocalDate dateFrom = LocalDate.of(2014, Month.JANUARY, 1);
        LocalDate dateTo = LocalDate.of(2014, Month.DECEMBER, 31);
        // execute
        notificator.generateAndSendHtmlSalaryReport("10", dateFrom, dateTo, "somebody@gmail.com");
        // verify results
        String expectedReportPath = "src/test/resources/expectedReport.html";
        assertActualReportEqualsTo(mockMimeMessageHelper, expectedReportPath);
    }

    private void assertActualReportEqualsTo(MimeMessageHelper mockMimeMessageHelper, String expectedReportPath) throws MessagingException, IOException {
        ArgumentCaptor<String> messageTextArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockMimeMessageHelper).setText(messageTextArgumentCaptor.capture(), anyBoolean());
        Path path = Paths.get(expectedReportPath);
        String expectedReportContent = new String(Files.readAllBytes(path));
        assertEquals(messageTextArgumentCaptor.getValue(), expectedReportContent);
    }

    private ResultSet getMockedResultSet(Connection someFakeConnection) throws SQLException {
        PreparedStatement someFakePreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(someFakePreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(someFakeConnection.prepareStatement(anyString())).thenReturn(someFakePreparedStatement);
        when(mockResultSet.next()).thenReturn(true, true, false);
        return mockResultSet;
    }

    private MimeMessageHelper getMockedMimeMessageHelper() throws Exception {
        JavaMailSenderImpl mockMailSender = mock(JavaMailSenderImpl.class);
        MimeMessage mockMimeMessage = mock(MimeMessage.class);
        when(mockMailSender.createMimeMessage()).thenReturn(mockMimeMessage);
        whenNew(JavaMailSenderImpl.class).withNoArguments().thenReturn(mockMailSender);
        MimeMessageHelper mockMimeMessageHelper = mock(MimeMessageHelper.class);
        whenNew(MimeMessageHelper.class).withArguments(any(MimeMessage.class), anyBoolean()).thenReturn(mockMimeMessageHelper);
        return mockMimeMessageHelper;
    }

}
