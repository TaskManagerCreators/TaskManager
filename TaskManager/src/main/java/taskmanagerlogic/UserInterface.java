package taskmanagerlogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

/**
 * Present interacts with ending users
 *
 * @version 1.0
 */
public class UserInterface {

    private static Journal journal;

    /**
     * Pattern of tasks date-field
     */
    public static String datePattern = "^([1 - 12]):(((0[0-9])|([1-5][0-9]))) ((am)|(pm)) ((0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).(20\\d\\d))$";


    /**
     * Realize interacts with user
     * Recognizes the user input commands
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        journal = Journal.getInstance();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String in, key, argument;
        String command[];
        System.out.println("Hey , i'm your task manager.Create task already now.");
        while (true) {
            in = input.readLine().toLowerCase().trim();
            command = in.split(",");
            key = command[0];
            key = key.substring(0, key.split(" ").length < 2 ? key.length() : key.indexOf(" "));
            switch (key) {
                case "create": {
                    try {
                        create(Arrays.toString(command));
                        continue;
                    } catch (Exception e) {
                        System.out.println("Arguments error\ni.e. - create [name , describe , time , contacts].\n" +
                                "time in format hh:mm am|pm dd.mm.YYYY\nhelp - show all commands");
                        continue;
                    }
                }
                case "show": {
                    if (journal.getTasks().size() == 0) System.out.println("Empty.");

                    for (Task t : journal.getTasks()) {
                        System.out.println("[" + t.getName() + " " + t.getDescribe() + " " + t.getDateTime() + " " + t.getContacts());
                    }
                    continue;
                }

                case "delete": {
                    try {
                        argument = command[0].substring(command[0].indexOf(" ")).trim();
                        if (journal.getTasks().isEmpty() || argument == "") {
                            throw new NullPointerException();
                        }
                        if (isDigit(argument)) {
                            journal.delete(Integer.valueOf(argument));
                        } else {
                            Matcher matcher = Pattern.compile(datePattern).matcher(argument);
                            if (!matcher.matches()) throw new DataFormatException();
                            journal.delete(argument);
                        }
                        System.out.println("Command 'delete' executed successfully.");
                        continue;
                    } catch (NullPointerException | DataFormatException | IndexOutOfBoundsException e) {
                        System.out.println("No such tasks\nCommand 'delete' executed filed.");
                        continue;
                    }

                }

                case "help": {
                    help();
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
                    input.close();
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

    private static void help() {
        String result = "Commands: \ncreate [name , describe , time , contacts] - create task\n";
        result += "delete [index | time] - delete task by index or  time\n";
        result += "Another commands : show , clean , save , exit.";
        System.out.println(result);
    }

    /**
     * Splits "create" command , divides arguments and create task
     * Compares data of dateTime with pattern
     *
     * @param command
     * @throws DataFormatException
     * @see UserInterface#datePattern
     */
    private static void create(String command) throws DataFormatException {
        String name, describe, dateTime, contacts;


        String[] params = command.split(",");

        name = params[0].substring(params[0].indexOf(" ")).trim();
        describe = params[1].trim();
        dateTime = params[2].trim();
        contacts = params[3].trim();


        if (!dateMatcher(dateTime))
            throw new DataFormatException();

        Task task = new Task(name, describe, dateTime, contacts);

        journal.add(task);

    }

    public static boolean dateMatcher(String date) {
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static boolean isDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < 48 || s.charAt(i) > 57) return false;
        }
        return true;
    }

}
