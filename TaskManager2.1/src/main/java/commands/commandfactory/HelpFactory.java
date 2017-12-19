package commands.commandfactory;

import commands.Command;
import taskmanagerlogic.Journal;
/**
 * A factory that produces a help command.
 * @see commands.Help
 * @version 1.0
 */
public class HelpFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
