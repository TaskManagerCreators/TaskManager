package taskManagerCreators.commands.commandfactory;

import taskManagerCreators.commands.Command;
import taskManagerCreators.taskmanagerlogic.Journal;
/**
 * A factory that produces a delete command.
 * @see taskManagerCreators.commands.Delete
 * @version 1.0
 */
public class DeleteFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
