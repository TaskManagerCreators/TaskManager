package commands;

import org.springframework.stereotype.Component;
import taskmanagerlogic.Action;
import taskmanagerlogic.InterAction;
import taskmanagerlogic.Journal;
import taskmanagerlogic.Task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.DataFormatException;


/**
 * This class encapsulates user-entered "show" command
 *
 * @see InterAction - Used in interaction with ending users
 * Is multi-threaded
 */
@Component("show")
public class Show implements Command {

    private Journal journal;

    private String command;

    //@Autowired
    public Show(Journal journal) {
        this.journal = journal;
    }

    public Show() {
    }

    public Show(String command) {
        this.command = command;
    }

    public Show(String command, Journal journal) {
        this.command = command;
        this.journal = journal;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(String command) throws ParseException, DataFormatException {
        String arg;
        arg = command.substring(command.indexOf('-') + 1);

        try {
            if (arg == command)
                System.out.println(journal);
            else collate(arg);
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException | ParseException e) {

        }
    }

    /**
     * Splits "show" command , divides arguments and create task
     *
     * @param arguments
     * @throws DataFormatException
     */
    private void collate(String arguments) throws ParseException {
        List<Task> temp;
        String data, name;
        UUID id;
        Date from, to;
        Action status;
        String key = arguments.substring(0, arguments.indexOf(' '));
        data = arguments.substring(arguments.indexOf(key) + key.length()).trim();

        if (data.length() == 0)
            throw new IllegalArgumentException();

        switch (key) {
            case Command.state:
                if (!data.equals("scheduled") &&
                        !data.equals("completed") &&
                        !data.equals("running")) {
                    throw new IllegalArgumentException();
                }
                status = Enum.valueOf(Action.class, data.toUpperCase());
                temp = journal.findByStatus(status);
                break;

            case Command.id:
                id = UUID.fromString(data.trim());
                temp = new ArrayList<>();
                temp.add(journal.findById(id));
                break;

            case Command.name:
                name = data.trim();
                temp = journal.findByName(name);
                break;

            case Command.date:
                from = dateFormat.parse(data.substring(0, data.indexOf("to")).trim());
                to = dateFormat.parse(data.substring(data.indexOf("to") + 2, data.length()).trim());
                if (from.after(to))
                    throw new IllegalArgumentException();
                temp = journal.findByPeriodOfTime(from, to);
                break;

            default:
                throw new IllegalArgumentException();
        }

        if (temp == null)
            System.out.println("No data.");
        else
            temp.forEach((task) -> System.out.println(task));

    }

    @Override
    public void run() {
        try {
            execute(command);
        } catch (ParseException | DataFormatException e) {
            System.out.println("Arguments error.");
        }
    }
}

