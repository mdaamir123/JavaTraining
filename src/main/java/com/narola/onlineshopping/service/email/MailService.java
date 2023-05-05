package com.narola.onlineshopping.service.email;

import com.narola.onlineshopping.OnlineShoppingApplication;
import com.narola.onlineshopping.model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class MailService {

    public static void sendMail(User user) {

        final String username = System.getenv("email.username");
        final String password = System.getenv("email.password");

        Properties props = new Properties();
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "smtp.1and1.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.1and1.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Welcome to OnlineShopping");
            message.setText("Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\nHere, is your verification code: " + user.getVerificationPin());
            Transport.send(message);

        }
        catch (MessagingException e) {
            System.out.println("Error sending message: " + e.getMessage());
            System.out.println("Kindly retry.");
            OnlineShoppingApplication.main(null);
        }

    }
}
