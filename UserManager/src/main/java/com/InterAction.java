package com;

import com.controllers.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component("InterAction")
@ComponentScan(basePackages = "com")
public class InterAction {
    public static ApplicationContext context;
    private UserController controller;
    @Autowired
    public InterAction (UserController controller){
        this.controller = controller;
    }

    public static void main(String[] args) {
        context = SpringApplication.run(InterAction.class);
        InterAction interAction = (InterAction) (context.getBean("InterAction"));
        interAction.work();

    }
    public void work(){
        System.out.println("I`m working.");

    }
}
