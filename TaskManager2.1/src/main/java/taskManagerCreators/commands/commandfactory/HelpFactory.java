package taskManagerCreators.commands.commandfactory;

import taskManagerCreators.commands.Command;
import taskManagerCreators.taskmanagerlogic.Journal;
/**
 * A factory that produces a help command.
 * @see taskManagerCreators.commands.Help
 * @version 1.0
 */
public class HelpFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
