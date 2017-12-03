package taskmanagerlogic;

import config.TaskManagerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import commands.Command;
import commands.Create;
import commands.Delete;
import commands.Show;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.zip.DataFormatException;

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
        ui.setTimer(new Timer());
        ui.setCurrentTask(ui.searchTask());
        if (!Objects.isNull(ui.getCurrentTask().getId())) {
            ui.callback();
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String in, key;
        String command[];
        System.out.println("Hey , i'm your task manager.Create task right now.");
        // Cleaner cleaner = new Cleaner(ui);

        while (true) {
            in = input.readLine().toLowerCase().trim();

            if (ui.getCurrentTask().getStatus() == Action.RUNNING || Objects.isNull(ui.getCurrentTask())) {
                if (ui.getJournal().getTasks().size() > 1) {
                    ui.setCurrentTask(ui.searchTask());
                    ui.callback();
                }
            }

            command = in.split(",");
            key = command[0];
            key = key.substring(0, key.split(" ").length < 2 ? key.length() : key.indexOf(" "));
            switch (key) {
                case "create": {
                    try {
                        operation = new Create(ui.getJournal());
                        operation.execute(Arrays.toString(command));
                        if (ui.getJournal().getTasks().size() == 1) {
                            ui.setCurrentTask(ui.searchTask());
                            ui.callback();
                        }
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
                            operation = new Show(ui.getJournal());
                            try {
                                operation.execute(command[0]);
                            } catch (ParseException | DataFormatException e) {
                                System.out.println("Error");
                            }
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
                        operation = new Delete(ui.getJournal());
                        try {
                            operation.execute(command[0]);
                        } catch (ParseException | DataFormatException e) {
                            e.printStackTrace();
                        }
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
                    ui.getTimer().cancel();
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



