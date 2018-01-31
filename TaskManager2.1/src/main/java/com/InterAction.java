package com;

import com.commands.Command;
import com.mailservice.EmailServiceImpl;
import com.taskmanagerlogic.Cleaner;
import com.taskmanagerlogic.CommandResolver;
import com.taskmanagerlogic.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interacts with ending users
 *
 * @version 1.0
 */
@SpringBootApplication
@Component("InterAction")
public class InterAction {

    private Journal journal;

    private Cleaner cleaner;

    private Command command;


    @Autowired
    private EmailServiceImpl emailService;

    public static ApplicationContext applicationContext;

    private ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();


    @Autowired
    public InterAction(Cleaner cleaner, Journal journal) {
        this.cleaner = cleaner;
        this.journal = journal;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        applicationContext = SpringApplication.run(InterAction.class);
        InterAction interAction = (InterAction) (applicationContext.getBean("InterAction"));
        interAction.communicate();
    }


    /**
     * Realize interacts with user
     * Recognizes the user input com.commands
     *
     * @throws IOException
     */
    public void communicate() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String in, commandPart[];
        if (!journal.getTasks().isEmpty()) {
            journal.schedule();
        }

        executor.setMaxPoolSize(10);
        executor.initialize();

        System.out.println("Hey , i'm your task manager right now.");

        emailService.sendSimpleMessage("archac3@gmail.com" , "Hey" , "Hey");

        //create task , desc , 12:48 31.01.2018 , output - 1000 , Jelly , KAte
        while (true) {
            in = input.readLine().trim();
            commandPart = in.split(",");
            try {
                command = CommandResolver.createCommand(commandPart);
                executor.execute(command, 1000);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}



