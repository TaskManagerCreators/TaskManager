package com.commands;

import org.springframework.beans.factory.annotation.Autowired;
import com.taskmanagerlogic.InterAction;
import com.taskmanagerlogic.Journal;

import java.text.ParseException;
import java.util.zip.DataFormatException;

/**
 * This class encapsulates user-entered "clean" command
 *
 * @version 1.0
 * @see InterAction - Used in interaction with ending users
 * Is multi-threaded
 */
public class Clean implements Command {

    private Journal journal;


    @Override
    public void execute(String ...  args) throws ParseException, DataFormatException {
        journal.clean();
        System.out.println("Command 'clean' executed successfully.");
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (ParseException | DataFormatException e) {
            e.printStackTrace();
        }
    }


    @Autowired
    public void setJournal(Journal journal) {
        this.journal = journal;
    }

}
