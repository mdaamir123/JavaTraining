package com.narola.onlineshoppingV1.config;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class MailConfig {
    public static MailConfig mailConfig = null;
    private static Properties properties = null;

    private String username = System.getenv("email.username");
    private String password = System.getenv("email.password");

    private MailConfig() {

    }

    public static MailConfig getInstance() {
        if (mailConfig == null) {
            mailConfig = new MailConfig();
        }
        return mailConfig;
    }

    public Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
            properties.put("mail.smtp.ssl.trust", "smtp.1and1.com");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.1and1.com");
            properties.put("mail.smtp.port", "587");
        }
        return properties;
    }

    public Session buildSession() {
        return Session.getInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public String getUserName() {
        return username;
    }
}
