package com.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import com.taskmanagerlogic.History;
import com.taskmanagerlogic.InterAction;

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
@ComponentScan(basePackages ={"com.taskmanagerlogic"} )
@Component
public class ShowHistory implements Command {

    private MongoTemplate mongoTemplate;


    public ShowHistory() {

    }

    @Override
    public void execute(String... command) throws ParseException, DataFormatException, IOException {
        mongoTemplate.findAll(History.class);
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
