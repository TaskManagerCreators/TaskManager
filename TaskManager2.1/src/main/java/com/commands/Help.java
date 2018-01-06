package com.commands;

import com.taskmanagerlogic.InterAction;

import java.text.ParseException;
import java.util.zip.DataFormatException;
/**
 * This class encapsulates user-entered "help" command
 *
 * @see InterAction - Used in interaction with ending users
 * Is multi-threaded
 * @version 1.0
 */
public class Help implements Command {

    @Override
    public void execute(String ...  command) throws ParseException, DataFormatException {
        System.out.println("This is help. Please , write correctly");
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (ParseException | DataFormatException e) {
            e.printStackTrace();
        }
    }
}
