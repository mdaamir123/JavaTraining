package com.narola.onlineshopping.config;

import java.util.Properties;

public class MailConfig {
    public static MailConfig mailConfig = null;
    private static Properties properties = null;

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
}
