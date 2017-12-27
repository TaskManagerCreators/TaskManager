package taskManagerCreators.commands;

import taskManagerCreators.taskmanagerlogic.InterAction;

import java.io.IOException;
import java.text.ParseException;
import java.util.zip.DataFormatException;
/**
 * This class encapsulates user-entered "exit" command
 *
 * @see InterAction - Used in interaction with ending users
 * Is multi-threaded
 * @version 1.0
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
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
