package taskmanagerlogic;

import commands.Command;
import config.TaskManagerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Present interacts with ending users
 *
 * @version 1.0
 */

@Component("InterAction")
@ComponentScan({"taskmanagerlogic", "commands"})
public class InterAction {

    private Journal journal;

    private Cleaner cleaner;

    @Qualifier("create")
    @Autowired
    private Command create;

    @Qualifier("show")
    @Autowired
    private Command show;

    @Qualifier("delete")
    @Autowired
    private Command delete;

    private ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    private static AnnotationConfigApplicationContext context;

    @Autowired
    public InterAction(Cleaner cleaner, Journal journal) {
        this.cleaner = cleaner;
        this.journal = journal;
    }

    public InterAction() {

    }

    /**
     * Realize interacts with user
     * Recognizes the user input commands
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        context = new AnnotationConfigApplicationContext();
        context.register(TaskManagerConfig.class);
        context.refresh();
        SpringApplication.run(Cleaner.class);
        InterAction interAction = (InterAction) context.getBean("InterAction");
        interAction.communicate();
    }


    public void communicate() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String in, key;
        String command[];
        journal.schedule();
        executor.setMaxPoolSize(10);
        executor.initialize();
        System.out.println("Hey , i'm your task manager.Create task right now.");
        while (true) {
            in = input.readLine().toLowerCase().trim();
            command = in.split(",");
            key = command[0];
            key = key.substring(0, key.split(" ").length < 2 ? key.length() : key.indexOf(" "));
            switch (key) {
                case "create": {
                    create.setCommand(Arrays.toString(command));
                    executor.execute(create, 1000);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    journal.schedule(journal.getLast());
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
                    UserInterface.help();
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
            }
            break;
        }
    }

}



