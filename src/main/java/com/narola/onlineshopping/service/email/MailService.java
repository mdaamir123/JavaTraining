package com.narola.onlineshopping.service.email;

import com.narola.onlineshopping.config.MailConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class MailService {

    public static void sendMail(String to, String subject, String text) throws MessagingException {

        final String username = System.getenv("email.username");
        final String password = System.getenv("email.password");

        Properties props = MailConfig.getInstance().getProperties();

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);

        } catch (MessagingException e) {
            throw new MessagingException("Something went wrong. Please try again later.", e);
        }

    }
}
