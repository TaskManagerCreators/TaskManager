package com.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import com.taskmanagerlogic.History;
import com.InterAction;

import java.io.IOException;
import java.text.ParseException;
import java.util.zip.DataFormatException;

/**
 * This class encapsulates user-entered "showhistory" command
 *
 * @version 1.0
 * @see InterAction - Used in interaction with ending users
 * Is multi-threaded
 */
@Component
@ComponentScan
public class ShowHistory implements Command {

    @Autowired
    private History history;

    public ShowHistory() {

    }

    @Override
    public void execute(String... command) throws ParseException, DataFormatException, IOException {
        history = (History) InterAction.applicationContext.getBean("history");
        System.out.println(history);
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (ParseException | DataFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
