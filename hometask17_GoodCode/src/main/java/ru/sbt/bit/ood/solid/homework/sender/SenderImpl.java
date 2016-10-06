package ru.sbt.bit.ood.solid.homework.sender;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SenderImpl implements Sender {

    @Override
    public void send(String recipient, String report) {
        try {
            // now when the report is built we need to send it to the recipients list
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            // we're going to use google mail to send this message
            mailSender.setHost("mail.google.com");
            // construct the message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipient);
            // setting message text, last parameter 'true' says that it is HTML format
            helper.setText(report, true);
            helper.setSubject("Monthly department salary report");
            // send the message
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
