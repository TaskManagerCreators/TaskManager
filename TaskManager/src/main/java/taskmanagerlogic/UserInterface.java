package taskmanagerlogic;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;


/**
 * Includes methods for interacts with ending users
 *
 * @version 1.0
 */
public class UserInterface {

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    private Journal journal;

    private Notification notification;

    public String getDatePattern() {
        return datePattern;
    }

    /**
     * Pattern of tasks date-field
     */
    private  String datePattern = "^((([1 - 12]):(((0[0-9])|([1-5][0-9]))) ((am)|(pm)))|((0?[0-9]|1[0-9]|2[0-3]):((0[0-9])|([1-5][0-9])))) ((0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).(20\\d\\d))$";

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     *
     */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");

    /**
     * Constant for deleting a task
     */
    protected static final int DELETE = 0;

    /**
     * Constant for searching a task
     */
    protected static final int SHOW = 1;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    /**
     *
     */
    private Timer timer;

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    /**
     * The next task that must be notified
     */
    private Task currentTask;


    public static void help() {
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
     * @see #datePattern
     */
    public void create(String command) throws DataFormatException, ParseException {
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
    public void deleteOrShow(String command, int operation) {
        String arg, data, name;
        List<Task> showingTasks = new ArrayList<>();
        List<Task> temp;
        UUID id;
        Date from, to;
        Action status;

        arg = command.substring(command.indexOf('-') + 1);

        try {
            if (arg.startsWith("e")) {
                data = arg.substring(arg.indexOf('e') + 1).trim();
                if (!data.equals("scheduled") &&
                        !data.equals("completed") &&
                        !data.equals("running") || data.length() == 0) {
                    throw new IllegalArgumentException();
                }
                status = Enum.valueOf(Action.class, data.toUpperCase());
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
                id = UUID.fromString(data.trim());
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
                name = data.trim();
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
                from = dateFormat.parse(data.substring(0, data.indexOf("to")).trim());
                to = dateFormat.parse(data.substring(data.indexOf("to") + 2, data.length()).trim());
                if (from.after(to))
                    throw new IllegalArgumentException();
                temp = journal.findByPeriodOfTime(from, to);
                if (operation == SHOW)
                    showingTasks.addAll(temp);
                else if (operation == DELETE)
                    journal.delete(from, to);
            }
            timer.cancel();
            timer.purge();
            timer = new Timer();
            Task t = searchTask();
            if(currentTask != t) {
                currentTask = t;
                if (!currentTask.getName().equals("")) {
                    callback();
                }
            }
            showingTasks.forEach((task -> System.out.println(task)));

            if (showingTasks.isEmpty() && operation == SHOW)
            System.out.println("Empty.");

        } catch (IllegalArgumentException | StringIndexOutOfBoundsException | ParseException e) {
            System.out.println("Arguments error\ni.e. - show [(-e true|false)?  (-n name)? (-id id)? (-d date)?].\n" +
                    "date in format hh:mm am|pm dd.mm.YYYY\nhelp - show all commands");
        }
    }


    public boolean dateMatcher(Date date) {
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(dateFormat.format(date));
        Date now = Date.from(Instant.now());

        if (date.before(now))
            return false;

        return matcher.matches();
    }

    protected Task searchTask() {
        List<Task> tasks = journal.getTasks();
        if (tasks.size() == 0) {
            return new Task();
        }
        Collections.sort(tasks, Task.COMPARE_BY_TIME);
        int i = 0;
        while (tasks.get(i).getStatus() == Action.RUNNING) {
            i++;
            if (i == tasks.size()) {
                return new Task();
            }
        }
        return tasks.get(i);
    }

    /**
     * Schedules to switch the current task status after a while
     */
    protected void callback() {
        if (Objects.isNull(timer)) {
            timer = new Timer();
        }
        long time;
        Date date = new Date();
        time = currentTask.getTargetTime().getTime() - date.getTime();
        if (time < 0) return;
        timer.schedule(currentTask, time);

    }

    protected void doNext() {
        currentTask = searchTask();
        if (currentTask.getId() != null) {
            callback();
        }
    }


    public UserInterface(){
        journal = new Journal();
        journal.load(new File("journal.txt"));
    }


}
