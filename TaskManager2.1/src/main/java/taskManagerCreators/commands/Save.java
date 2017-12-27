package taskManagerCreators.commands;

import taskManagerCreators.taskmanagerlogic.InterAction;
import taskManagerCreators.taskmanagerlogic.Journal;

import java.io.IOException;
import java.text.ParseException;
import java.util.zip.DataFormatException;
/**
 * This class encapsulates user-entered "save" command
 *
 * @see InterAction - Used in interaction with ending users
 * Is multi-threaded
 * @version 1.0
 */
public class Save implements Command {

    private Journal journal;

    public Save(Journal journal) {
        this.journal = journal;
    }

    @Override
    public void execute(String command) throws ParseException, DataFormatException, IOException {
        journal.save();
        System.out.println("Command 'save' executed successfully.");
    }

    @Override
    public void run() {
        try {
            execute(null);
        } catch (ParseException | DataFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
