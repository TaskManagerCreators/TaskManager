package com.mailservice;

import com.InterAction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailServiceImplTest {
    ApplicationContext context;
    EmailServiceImpl mailSender;
    @BeforeAll
    void setUp(){
        context = SpringApplication.run(InterAction.class);
        mailSender = context.getBean(EmailServiceImpl.class);
    }
    @Test
    void sendSimpleMessage() {
        assertThrows(MailAuthenticationException.class , ()->mailSender.sendSimpleMessage("me" , "Hi" , "Hi"));
    }
}