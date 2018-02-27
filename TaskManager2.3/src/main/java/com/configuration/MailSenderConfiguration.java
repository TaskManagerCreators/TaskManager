package com.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class MailSenderConfiguration {
    @Bean
    public JavaMailSender getJavaMailSender() {
        /*JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties properties = new Properties();

        try (InputStream input = new FileInputStream("src/main/resources.properties")) {
            properties.load(input);
            mailSender.setHost(properties.getProperty("host"));
            mailSender.setPort(587);
            mailSender.setUsername(properties.getProperty("username"));
            mailSender.setPassword("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties prop = mailSender.getJavaMailProperties();
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.debug", "true");*/
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("archac3@gmail.com");
        mailSender.setPassword("3X7G6I09QT");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");


        return mailSender;
    }
}
