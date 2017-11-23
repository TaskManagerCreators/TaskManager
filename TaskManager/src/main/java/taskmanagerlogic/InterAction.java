package taskmanagerlogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;

import static taskmanagerlogic.UserInterface.*;

/**
 * Present interacts with ending users
 *
 * @version 1.0
 */
public class InterAction {

    /**
     * Realize interacts with user
     * Recognizes the user input commands
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        journal = new Journal();
        journal.load(new File("journal.txt"));
        timer = new Timer();
        currentTask = searchTask();
        if (!Objects.isNull(currentTask.getId())) {
            callback();
        }
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String in, key;
        String command[];
        System.out.println("Hey , i'm your task manager.Create task right now.");
        while (true) {
            in = input.readLine().toLowerCase().trim();
            if (currentTask.getStatus() == Action.RUNNING || Objects.isNull(currentTask)) {
                if (journal.getTasks().size() > 1) {
                    currentTask = searchTask();
                    callback();
                }
            }
            command = in.split(",");
            key = command[0];
            key = key.substring(0, key.split(" ").length < 2 ? key.length() : key.indexOf(" "));
            switch (key) {
                case "create": {
                    try {
                        UserInterface.create(Arrays.toString(command));
                        if (journal.getTasks().size() == 1) {
                            currentTask = searchTask();
                            callback();
                        }
                        continue;
                    } catch (Exception e) {
                        System.out.println("Arguments error\ni.e. - create [name , describe , time , contacts].\n" +
                                "time in format hh:mm am|pm dd.mm.YYYY\nhelp - show all commands");
                        continue;
                    }
                }
                case "show": {
                    if (journal.getTasks().size() == 0)
                        System.out.println("Empty.");
                    else {
                        if (!command[0].trim().equals("show")) {
                            UserInterface.deleteOrShow(command[0], UserInterface.SHOW);
                        } else journal.getTasks().forEach(task -> System.out.println(task));
                    }
                    continue;
                }

                case "delete": {
                    if (journal.getTasks().size() == 0)
                        System.out.println("Empty.");
                    else {
                        UserInterface.deleteOrShow(command[0], UserInterface.DELETE);
                    }
                    System.out.println("Command 'delete' executed successfully.");
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
                    timer.cancel();
                    break;
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
