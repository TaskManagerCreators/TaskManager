package taskmanagerlogic;



import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
    public static String datePattern = "^((([1 - 12]):(((0[0-9])|([1-5][0-9]))) ((am)|(pm)))|((0?[0-9]|1[0-9]|2[0-3]):((0[0-9])|([1-5][0-9])))) ((0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).(20\\d\\d))$";

    /**
     *
     */
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");

    /**
     * Realize interacts with user
     * Recognizes the user input commands
     *
     * @param args
     * @throws IOException
     */
    /**
     * Constant for deleting a task
     */
    private static final int DELETE=0;
    /**
     * Constant for searching a task
     */
    private static final int SHOW=1;
    /**
     *
     */
    private static Timer timer;
    /**
     * The next task that must be notified
     */
    private static Task currentTask;

    public static void main(String[] args) throws IOException {
        journal = new Journal();
        journal.load(new File("journal.txt"));
        timer = new Timer();
        currentTask = searchTask();
        if(!Objects.isNull(currentTask.getId())) {callback();}
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String in, key;
        String command[];
        System.out.println("Hey , i'm your task manager.Create task right now.");
        while (true) {
            if(currentTask.isExecuted() || Objects.isNull(currentTask)){
                //journal.delete(currentTask.getId());
                if(journal.getTasks().size() > 1) {
                    currentTask = searchTask();
                    callback();
                }
            }
            in = input.readLine().toLowerCase().trim();
            command = in.split(",");
            key = command[0];
            key = key.substring(0, key.split(" ").length < 2 ? key.length() : key.indexOf(" "));
            switch (key) {
                case "create": {
                    try {
                        create(Arrays.toString(command));
                        if(journal.getTasks().size() == 1){
                            currentTask = searchTask();
                            callback();
                        }

                    } catch (ParseException | DataFormatException ex) {
                        System.out.println("Arguments error\ni.e. - create [name , describe , time , contacts].\n" +
                                "time in format hh:mm am|pm dd.mm.YYYY\nhelp - show all commands");
                        continue;
                    }
                    continue;
                }
                case "show": {
                    if (journal.getTasks().size() == 0)
                        System.out.println("Empty.");
                    else {
                        if (!command[0].trim().equals("show")) {
                            deleteOrShow(command[0], SHOW);
                        } else journal.getTasks().forEach(task -> System.out.println(task));
                    }
                    continue;
                }

                case "delete": {
                    if (journal.getTasks().size() == 0)
                        System.out.println("Empty.");
                    else {
                        deleteOrShow(command[0], DELETE);
                    }
                    System.out.println("Command 'delete' executed successfully.");
                    continue;
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
                    journal.save();
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
        result += "Other commands : show , clean , save , exit.";
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
    private static void create(String command) throws DataFormatException, ParseException {
        String name, describe;
        List<String> contacts;
        Date dateTime;

        String[] params = command.split(",");

        name = params[0].substring(params[0].indexOf(" ")).trim();
        describe = params[1].trim();
        dateTime = dateFormat.parse(params[2].trim());
        contacts = new ArrayList<>();

        for (int i = 3; i < params.length; i++) {
            contacts.add(params[i].trim());
        }

        if (!dateMatcher(dateTime))
            throw new DataFormatException();

        Task task = new Task(name, describe, dateTime, contacts);

        journal.add(task);

    }

    /**
     * @param command
     * @param operation "delete" or "show"
     */
    private static void deleteOrShow(String command, int operation) {
        String arg, data;
        List<Task> showingTasks = new ArrayList<>();
        List<Task> temp;
        arg = command.substring(command.indexOf('-') + 1);
        try {
            if (arg.startsWith("e")) {
                data = arg.substring(arg.indexOf('e') + 1).trim();
                if (!data.equals("true") && !data.equals("false") || data.length() == 0) {
                    throw new IllegalArgumentException();
                }
                boolean status = Boolean.valueOf(data);
                temp = journal.findByStatus(status);
                if (operation == SHOW)
                    showingTasks.addAll(temp);
                else if (operation == DELETE)
                    journal.delete(status);
            }

            if (arg.startsWith("id")) {
                data = arg.substring(arg.indexOf("id") + 2).trim();
                if (data.length() == 0)
                    throw new IllegalArgumentException();
                UUID id = UUID.fromString(data.trim());
                temp = journal.findById(id);
                if (operation == SHOW)
                    showingTasks.addAll(temp);
                else if (operation == DELETE)
                    journal.delete(id);
            }

            if (arg.startsWith("n")) {
                data = arg.substring(arg.indexOf('n') + 1).trim();
                if (data.length() == 0)
                    throw new IllegalArgumentException();
                String name = data.trim();
                temp = journal.findByName(name);
                if (operation == SHOW)
                    showingTasks.addAll(temp);
                else if (operation == DELETE)
                    journal.delete(name);
            }

            if (arg.startsWith("d")) {
                data = arg.substring(arg.indexOf("d") + 1).trim();
                if (data.length() == 0)
                    throw new IllegalArgumentException();
                Date from = dateFormat.parse(data.substring(0, data.indexOf("to")).trim());
                Date to = dateFormat.parse(data.substring(data.indexOf("to") + 2, data.length()).trim());
                if (from.after(to))
                    throw new IllegalArgumentException();
                temp = journal.findByPeriodOfTime(from, to);
                if (operation == SHOW)
                    showingTasks.addAll(temp);
                else if (operation == DELETE)
                    journal.delete(from, to);
            }

            showingTasks.forEach((task -> System.out.println(task)));

            if (showingTasks.isEmpty() && operation == SHOW)
                System.out.println("Empty.");

        } catch (IllegalArgumentException | StringIndexOutOfBoundsException | ParseException e) {
            System.out.println("Arguments error\ni.e. - show [(-e true|false)?  (-n name)? (-id id)? (-d date)?].\n" +
                    "date in format hh:mm am|pm dd.mm.YYYY\nhelp - show all commands");
        }
    }

    public static boolean dateMatcher(Date date) {
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(dateFormat.format(date));
        Date now = Date.from(Instant.now());

        if (date.before(now))
            return false;

        return matcher.matches();
    }

    public static boolean isDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < 48 || s.charAt(i) > 57) return false;
        }
        return true;
    }

    private static Task searchTask(){
        List<Task> tasks = journal.getTasks();
        if(tasks.size() == 0){return new Task();}
        Collections.sort(tasks,Task.COMPARE_BY_TIME);
        int i=0;
        while(tasks.get(i).isExecuted()){
            i++;
            if(i == tasks.size()){
                return new Task();
            }
        }
        return tasks.get(i);
    }

    /**
     * Schedules to switch the current task status after a while
     */
    private static void callback(){
        if(Objects.isNull(timer)) {timer = new Timer();}
        long time;
        Date date = new Date();
        time = currentTask.getDateTime().getTime() - date.getTime();
        if(time < 0) return;
        timer.schedule(currentTask,time);

    }
    protected static void doNext(){
        //journal.delete(currentTask.getId());
            currentTask = searchTask();
            if(currentTask.getId() != null) {
                callback();
            }
    }


}
