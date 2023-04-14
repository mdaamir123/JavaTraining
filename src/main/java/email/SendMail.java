package email;

import model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;


public class SendMail {
    public static void sendMail(User user) {

        final String username = System.getenv("email.username");
        final String password = System.getenv("email.password");

        String to = user.getEmail();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.ionos.com");
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Welcome to OnlineShopping.");
            message.setText("Dear " + user.getFirstName() + " " + user.getLastName() + ". Here, is your verification code: " + user.getVerificationPin());

            Transport.send(message);

            System.out.println("Message sent successfully.");

        } catch (MessagingException e) {
            System.out.println("Error sending message: " + e.getMessage());
            System.exit(0);
        }
    }
}
