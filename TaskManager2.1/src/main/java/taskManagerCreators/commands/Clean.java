package taskManagerCreators.commands;

import taskManagerCreators.taskmanagerlogic.InterAction;
import taskManagerCreators.taskmanagerlogic.Journal;

import java.text.ParseException;
import java.util.zip.DataFormatException;
/**
 * This class encapsulates user-entered "clean" command
 *
 * @see InterAction - Used in interaction with ending users
 * Is multi-threaded
 * @version 1.0
 */
public class Clean implements Command {

    private Journal journal;

    public Clean(Journal journal) {
        this.journal = journal;
    }


    @Override
    public void execute(String command) throws ParseException, DataFormatException {
        journal.clean();
        System.out.println("Command 'clean' executed successfully.");
    }

    @Override
    public void run() {
        try {
            execute(null);
        } catch (ParseException | DataFormatException e) {
            e.printStackTrace();
        }
    }
}
