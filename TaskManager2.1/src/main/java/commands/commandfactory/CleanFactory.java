package commands.commandfactory;

import commands.Command;
import taskmanagerlogic.Journal;

/**
 * A factory that produces a clean command.
 * @see commands.Clean
 * @version 1.0
 */
public class CleanFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
