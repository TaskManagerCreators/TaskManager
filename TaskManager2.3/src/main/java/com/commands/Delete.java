package com.commands;

import com.InterAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.taskmanagerlogic.*;

import java.text.ParseException;
import java.util.*;
import java.util.zip.DataFormatException;

/**
 * This class encapsulates user-entered "delete" command
 *
 * @version 1.0
 * @see InterAction - Used in interaction with ending users
 * Is multi-threaded
 */
@Component("delete")
public class Delete implements Command {

    private Journal journal;

    private String[] command;

    public Delete(String[] command) {
        this.command = command;
    }

    public Delete() {

    }

    @Autowired
    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    @Override
    public void execute(String... command) throws ParseException, DataFormatException {
        String arg;

        arg = command[0].substring(command[0].indexOf('-') + 1);

        journal = (Journal) InterAction.applicationContext.getBean("journal");

        try {
            collate(arg);
            System.out.println("Command 'delete' executed successfully.");
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException | ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * Splits "delete" command , divides arguments and create task
     *
     * @param arguments
     * @throws DataFormatException
     */
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
        } catch (ParseException | DataFormatException e) {
            System.out.println("Arguments error.");
        }
    }


}
