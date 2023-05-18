package com.narola.onlineshoppingV1.service;

import com.narola.onlineshoppingV1.config.MailConfig;
import com.narola.onlineshoppingV1.exception.MailException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
    public static void sendMail(String to, String subject, String text) throws MailException {

        Session session = MailConfig.getInstance().buildSession();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MailConfig.getInstance().getUserName()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);

        } catch (MessagingException e) {
            throw new MailException("Something went wrong. Please try again later.", e);
        }
    }
}
