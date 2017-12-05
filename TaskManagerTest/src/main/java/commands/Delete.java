package commands;

import org.springframework.beans.factory.annotation.Autowired;
import taskmanagerlogic.Action;
import taskmanagerlogic.Journal;

import java.text.ParseException;
import java.util.*;
import java.util.zip.DataFormatException;


public class Delete implements Command {

    private Journal journal;
    private String command;
    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    //@Autowired
    public Delete(Journal journal , String command) {
        this.journal = journal;
        this.command = command;
    }

    @Override
    public void execute(String command) throws ParseException, DataFormatException {
        String arg;

        arg = command.substring(command.indexOf('-') + 1);

        try {
            collate(arg);
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException | ParseException e) {

        }

    }

    private void collate(String arguments) throws ParseException {
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
                journal.delete(status);
                break;

            case Command.id:
                id = UUID.fromString(data.trim());
                journal.delete(id);
                break;

            case Command.name:
                name = data.trim();
                journal.delete(name);
                break;

            case Command.date:
                from = dateFormat.parse(data.substring(0, data.indexOf("to")).trim());
                to = dateFormat.parse(data.substring(data.indexOf("to") + 2, data.length()).trim());
                if (from.after(to))
                    throw new IllegalArgumentException();
                journal.delete(from, to);
                break;

            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public void run() {
        try {
            execute(command);
        }
        catch (ParseException | DataFormatException e) {
                System.out.println("Error");
            }
    }
}
