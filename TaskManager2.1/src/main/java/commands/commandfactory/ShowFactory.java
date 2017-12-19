package commands.commandfactory;

import commands.Command;
import taskmanagerlogic.Journal;
/**
 * A factory that produces a show command.
 * @see commands.Show
 * @version 1.0
 */
public class ShowFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
