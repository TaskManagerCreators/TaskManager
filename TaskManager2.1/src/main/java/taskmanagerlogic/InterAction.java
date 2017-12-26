package taskmanagerlogic;

import commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import reaction.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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
    MongoTemplate mongoOperation;



    public static ApplicationContext applicationContext;


    private ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();


    @Autowired
    public InterAction(Cleaner cleaner, Journal journal) {
        this.cleaner = cleaner;
        this.journal = journal;
    }

    public InterAction() {

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //ApplicationContext context = new AnnotationConfigApplicationContext(TaskManagerConfig.class, DatabaseConfiguration.class);
        //System.out.println(context);
        //Service service = context.getBean(Service.class);
        //SpringApplication.run(InterAction.class);
        //context = new AnnotationConfigApplicationContext("config");
        //context.register(DatabaseConfiguration.class, TaskManagerConfig.class);
        //context.refresh();
        //SpringApplicationBuilder builder = new SpringApplicationBuilder(DatabaseConfiguration.class, TaskManagerConfig.class);
        //builder.context().refresh();
        //builder.run();
        //System.out.println(context);
        //applicationContext = SpringApplication.run(InterAction.class);
        //ApplicationContextProvider contextProvider = new ApplicationContextProvider();
        //System.out.println(contextProvider.getApplicationContext());
        applicationContext = SpringApplication.run(InterAction.class);

        InterAction interAction = (InterAction) (applicationContext.getBean("InterAction"));
        Journal journal = (Journal) applicationContext.getBean("journal");
        //System.out.println(journal.getMongoOperations());
        interAction.communicate();
    }


    /**
     * Realize interacts with user
     * Recognizes the user input commands
     *
     * @throws IOException
     */
    public void communicate() throws IOException {
        System.out.println(mongoOperation);
        Task task = new Task("task", "purpose", new Date(), new Output("test"),
                new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"})));
        //mongoOperation.insert(task);
        /*BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String in, commandPart[];
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
            /*try {
                command = CommandResolver.createCommand(commandPart);
                executor.execute(command, 1000);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }*/
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}



