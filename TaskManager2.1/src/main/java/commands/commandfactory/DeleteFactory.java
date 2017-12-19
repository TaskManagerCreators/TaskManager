package commands.commandfactory;

import commands.Command;
import taskmanagerlogic.Journal;
/**
 * A factory that produces a delete command.
 * @see commands.Delete
 * @version 1.0
 */
public class DeleteFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
