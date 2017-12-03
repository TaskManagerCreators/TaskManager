package taskmanagerlogic;

import commands.Command;
import commands.Create;
import commands.Delete;
import commands.Show;
import config.TaskManagerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

@Component
@SpringBootApplication
public class InterAction {


   // @Autowired
    public InterAction(UserInterface userInterface) {
        this.ui = userInterface;
    }

    public InterAction() {

    }


    public void setUi(UserInterface ui) {
        this.ui = ui;
    }

    //@Autowired
    private UserInterface ui;

    ///@Autowired
    public void setCleaner(Cleaner cleaner) {
        this.cleaner = cleaner;
    }

    //@Autowired
    private Cleaner cleaner;
    private ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    /**
     * Realize interacts with user
     * Recognizes the user input commands
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // ApplicationContext applicationContext = new AnnotationConfigApplicationContext(taskmanagerlogic.TaskManagerContext.class);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(TaskManagerConfig.class);
        //context.refresh();
        SpringApplication.run(Cleaner.class, args);
        //UserInterface ui = new UserInterface();
        InterAction interAction = new InterAction();
        interAction.executor();

    }


    public void executor() throws IOException {
        UserInterface ui = new UserInterface();
        System.out.println(cleaner);
        Command operation;
        ui.schedule();
        executor.setMaxPoolSize(10);
        executor.initialize();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String in, key;
        String command[];
        System.out.println("Hey , i'm your task manager.Create task right now.");
        // Cleaner cleaner = new Cleaner(ui);

        while (true) {
            in = input.readLine().toLowerCase().trim();


            command = in.split(",");
            key = command[0];
            key = key.substring(0, key.split(" ").length < 2 ? key.length() : key.indexOf(" "));
            switch (key) {
                case "create": {
                    try {
                        operation = new Create(ui.getJournal(),Arrays.toString(command));
                        executor.execute(operation,1000);
                        ui.schedule(ui.getJournal().getLast());
                        continue;
                    } catch (Exception e) {
                        System.out.println("Arguments error\ni.e. - create [name , describe , time , contacts].\n" +
                                "time in format hh:mm am|pm dd.mm.YYYY\nhelp - show all commands");
                        continue;
                    }
                }
                case "show": {
                    if (ui.getJournal().getTasks().isEmpty())
                        System.out.println("Empty.");
                    else {
                        if (!command[0].trim().equals("show")) {
                            operation = new Show(ui.getJournal(),command[0]);
                                executor.execute(operation,1000);

                        } else {
                            List<Task> tasks = ui.getJournal().getTasks();
                            for (Task task : tasks) {
                                System.out.println(task);
                            }
                        }
                    }
                    continue;
                }

                case "delete": {
                    if (ui.getJournal().getTasks().isEmpty())
                        System.out.println("Empty.");
                    else {
                        operation = new Delete(ui.getJournal(),command[0]);
                        executor.execute(operation , 1000);
                    }
                    System.out.println("Command 'delete' executed successfully.");
                    continue;
                }

                case "help": {
                    UserInterface.help();
                    continue;
                }

                case "save": {
                    ui.getJournal().save();
                    System.out.println("Command 'save' executed successfully.");
                    continue;
                }

                case "clean": {
                    ui.getJournal().clean();
                    System.out.println("Command 'clean' executed successfully.");
                    continue;
                }

                case "exit": {
                    ui.getJournal().save();
                    input.close();
                    //ui.getTimer().cancel();
                    break;
                }
                case "history": {
                    // System.out.println(ui.getJournal().getHistory());
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



