package com.taskmanagerlogic;

import com.commands.Command;
import com.reaction.Output;
import com.repositories.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

/**
 * Interacts with ending users
 *
 * @version 1.0
 */
@SpringBootApplication
@Component("InterAction")
@ComponentScan(basePackages = "com")
public class InterAction {

    private Journal journal;

    private Cleaner cleaner;

    private Command command;

    public static ApplicationContext applicationContext;

    private ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    @Autowired
    private JournalRepository journalRepository;

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
        Task task = new Task(
                "task",
                "purpose",
                new Date(),
                new Output("test"),
                new ArrayList<>(
                        Arrays.asList(
                                new String[]{"Kate", "Paul"}
                                )
                )
                );

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String in, commandPart[];
        if (!journal.getTasks().isEmpty()) {
            journal.schedule();
        }

        executor.setMaxPoolSize(10);
        executor.initialize();

        System.out.println("Hey , i'm your task manager.Create task right now.");

        //create task , desc , 00:00 12.12.2018 , output - 1000 , Jelly , KAte
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



