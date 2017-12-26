package commands;

import taskmanagerlogic.InterAction;

import java.io.IOException;
import java.text.ParseException;
import java.util.zip.DataFormatException;

/**
 * This class encapsulates user-entered "exit" command
 *
 * @version 1.0
 * @see InterAction - Used in interaction with ending users
 * Is multi-threaded
 */
public class Exit implements Command {
    @Override
    public void execute(String command) throws ParseException, DataFormatException, IOException {
        System.exit(0);
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
