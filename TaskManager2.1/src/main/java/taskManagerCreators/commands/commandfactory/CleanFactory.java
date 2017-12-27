package taskManagerCreators.commands.commandfactory;

import taskManagerCreators.commands.Command;
import taskManagerCreators.taskmanagerlogic.Journal;

/**
 * A factory that produces a clean command.
 * @see taskManagerCreators.commands.Clean
 * @version 1.0
 */
public class CleanFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
