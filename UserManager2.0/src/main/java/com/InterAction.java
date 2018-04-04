package com;

import com.controllers.UserController;
import com.model.User;
import com.secutiry.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Component;

import java.util.Collections;


@EnableResourceServer
@SpringBootApplication
@Component("InterAction")
@ComponentScan(basePackages = "com")
public class InterAction {

    public static ApplicationContext context;
    private UserController controller;

    @Autowired
    public InterAction(UserController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        context = SpringApplication.run(InterAction.class);
        InterAction interAction = (InterAction) (context.getBean("InterAction"));
        interAction.work();
    }

    public void work() {
        User test = new User();
        test.setPassword("pass");
        test.setEmail("admin");
        test.setName("adminName");
        test.setDescription("description");
        test.setAccountNonLocked(true);
        test.setCredentialsNonExpired(true);
        test.setAccountNonExpired(true);
        test.setEnabled(true);
        test.setAuthorities(Collections.singletonList(Role.SUPER_USER));
        controller.addUser(test);
        System.out.println("I`m working.");
    }

    public void clean() {
        //controller.deleteUserByEmail("mail");
    }
}
