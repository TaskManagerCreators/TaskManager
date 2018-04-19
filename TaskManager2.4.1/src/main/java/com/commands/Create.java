package com.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.reaction.*;
import com.InterAction;
import com.taskmanagerlogic.Journal;
import com.taskmanagerlogic.Task;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

/**
 * This class encapsulates user-entered "create" command
 *
 * @version 1.0
 * @see InterAction - Used in interaction with ending users
 * Is multi-threaded
 */

@Component
public class Create implements Command {


    private Journal journal;

    private String[] command;


    public Create() {

    }


    public Create(String[] command) {
        this.command = command;
    }

    @Autowired
    public void setJournal(Journal journal) {
        this.journal = journal;
    }


    /**
     * Splits "create" command , divides arguments and create task
     * Compares data of dateTime with pattern
     *
     * @param args
     * @throws DataFormatException
     * @see #datePattern
     */
    @Override
    public void execute(String... args) throws ParseException, DataFormatException {
        String name, describe;
        List<String> contacts;
        Date dateTime;
        Reaction reaction;

        journal = InterAction.applicationContext.getBean(Journal.class);


        if (args.length <= 3) throw new ArrayIndexOutOfBoundsException();

        name = args[0].trim();
        describe = args[1].trim();
        dateTime = dateFormat.parse(args[2].trim());


        reaction = parseReaction(args[3].trim());

        contacts = new ArrayList<>();

        for (int i = 4; i < args.length; i++) {
            contacts.add(args[i].trim());
        }

        if (!dateMatcher(dateTime))
            throw new DataFormatException();

        Task task = new Task(name, describe, dateTime, reaction, contacts);

        journal.add(task);

        journal.schedule(journal.getLast());
    }

    public static Reaction parseReaction(String data) {
        return ReactionResolver.create(data);
    }

    public boolean dateMatcher(Date date) {
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(dateFormat.format(date));
        Date now = Date.from(Instant.now());

        if (date.before(now))
            return false;

        return matcher.matches();
    }

    @Override
    public void run() {
        try {
            execute(command);
        } catch (ParseException | DataFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Arguments error.");
        }
    }
}
