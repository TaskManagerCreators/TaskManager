package commands.commandfactory;

import commands.Command;
import taskmanagerlogic.Journal;
/**
 * A factory that produces a save command.
 * @see commands.Save
 * @version 1.0
 */
public class SaveFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
