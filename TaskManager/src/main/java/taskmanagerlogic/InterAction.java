package taskmanagerlogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
    public static void main(String[] args) throws IOException, InterruptedException {
        UserInterface ui = new UserInterface();
        ui.setTimer(new Timer());
        ui.setCurrentTask(ui.searchTask());
        if (!Objects.isNull(ui.getCurrentTask().getId())) {
            ui.callback();
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String in, key;
        String command[];
        System.out.println("Hey , i'm your task manager.Create task right now.");
        Cleaner cleaner = new Cleaner(ui);
        cleaner.setDeltaOfTime(10000 * 6);
        cleaner.setDaemon(true);
        cleaner.start();

        while (true) {
            System.out.println(cleaner.getState());
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
                        ui.create(Arrays.toString(command));
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
                            ui.deleteOrShow(command[0], UserInterface.SHOW);
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
                        ui.deleteOrShow(command[0], UserInterface.DELETE);
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
                    System.out.println(ui.getJournal().getHistory());
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
