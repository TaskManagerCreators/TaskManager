package commands.commandfactory;

import commands.Command;
import taskmanagerlogic.Journal;
/**
 * A factory that produces a create command.
 * @see commands.Create
 * @version 1.0
 */
public class CreateFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
