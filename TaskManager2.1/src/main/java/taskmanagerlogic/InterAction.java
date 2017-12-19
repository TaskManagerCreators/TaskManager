package taskmanagerlogic;

import commands.Command;
import config.DatabaseConfiguration;
import config.TaskManagerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import reaction.Sleep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Interacts with ending users
 *
 * @version 1.0
 */
@SpringBootApplication
@Component("InterAction")
@ComponentScan({"taskmanagerlogic", "commands", "controller"})
public class InterAction {

    private Journal journal;

    private Cleaner cleaner;

    /*@Qualifier("create")
    @Autowired
    private Command create;

    @Qualifier("show")
    @Autowired
    private Command show;

    @Qualifier("delete")
    @Autowired*/
    private Command command;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    MongoOperations mongoOperations;

    private ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    /**
     * AnnotationConfigApplicationContext
     *
     * @see TaskManagerConfig
     */
    public static AnnotationConfigApplicationContext context;

    @Autowired
    public InterAction(Cleaner cleaner, Journal journal) {
        this.cleaner = cleaner;
        this.journal = journal;
    }

    public InterAction() {

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        context = new AnnotationConfigApplicationContext();
        context.register(DatabaseConfiguration.class, TaskManagerConfig.class);
        context.refresh();
        //MongoOperations mongoOperation = (MongoOperations) context.getBean("mongoTemplate");
        SpringApplication.run(Cleaner.class);
        InterAction interAction = (InterAction) context.getBean("InterAction");

       /* Task task = new Task(
                "Test",
                "Test",
                new Date(),
                new Sleep(1000),
                new ArrayList<>()
        );
        mongoOperation.save(task);
        System.out.println("Saved user : " + task);*/
        interAction.communicate();
    }

    /**
     * Realize interacts with user
     * Recognizes the user input commands
     *
     * @throws IOException
     */
    public void communicate() throws IOException {
        System.out.println(taskRepository);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String in, key;
        String commandPart[];
        if (!journal.getTasks().isEmpty()) {
            journal.schedule();
        }
        executor.setMaxPoolSize(10);
        executor.initialize();
        System.out.println("Hey , i'm your task manager.Create task right now.");
        while (true) {
            in = input.readLine().toLowerCase().trim();
            commandPart = in.split(",");
            //<editor-fold desc = "switch">
            /*switch (key) {
                case "create": {
                    try {
                        create.setCommand(Arrays.toString(command));
                        executor.execute(create, 1000);
                        //TODO: Fix
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    continue;
                }
                case "show": {
                    if (journal.getTasks().isEmpty())
                        System.out.println("Empty.");
                    else {
                        if (!command[0].trim().equals("show")) {
                            show.setCommand(command[0]);
                            executor.execute(show, 1000);
                        } else {
                            List<Task> tasks = journal.getTasks();
                            for (Task task : tasks) {
                                System.out.println(task);
                            }
                        }
                    }
                    continue;
                }

                case "delete": {
                    if (journal.getTasks().isEmpty())
                        System.out.println("Empty.");
                    else {
                        delete.setCommand(command[0]);
                        executor.execute(delete, 1000);
                        System.out.println("Command 'delete' executed successfully.");
                    }
                    continue;
                }

                case "help": {
                    System.out.println("Help will be written soon.");
                    continue;
                }

                case "save": {
                    journal.save();
                    System.out.println("Command 'save' executed successfully.");
                    continue;
                }

                case "clean": {
                    journal.clean();
                    System.out.println("Command 'clean' executed successfully.");
                    continue;
                }

                case "exit": {
                    journal.save();
                    input.close();
                    //SpringApplication.exit(context);
                    break;
                }
                case "history": {
                    System.out.println(journal.getHistory());
                    continue;
                }

                default: {
                    System.out.println("Check 'help'");
                    continue;
                }
            }*/
            //</editor-fold>
            try {
                command = CommandResolver.createCommand(commandPart);
                executor.execute(command, 1000);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }

}



