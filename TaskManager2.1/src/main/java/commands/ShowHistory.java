package commands;

import taskmanagerlogic.InterAction;
import taskmanagerlogic.Journal;

import java.io.IOException;
import java.text.ParseException;
import java.util.zip.DataFormatException;
/**
 * This class encapsulates user-entered "showhistory" command
 *
 * @see InterAction - Used in interaction with ending users
 * Is multi-threaded
 * @version 1.0
 */
public class ShowHistory implements Command {
    private Journal journal;

    public ShowHistory(Journal journal) {
        this.journal = journal;
    }

    @Override
    public void execute(String command) throws ParseException, DataFormatException, IOException {
        System.out.println(journal.getHistory());
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
