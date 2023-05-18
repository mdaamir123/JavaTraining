package com.narola.onlineshopping.service.email;

import com.narola.onlineshopping.config.MailConfig;
import com.narola.onlineshopping.exception.MailException;

import javax.mail.*;
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
