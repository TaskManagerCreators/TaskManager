package taskManagerCreators.commands.commandfactory;

import taskManagerCreators.commands.Command;
import taskManagerCreators.taskmanagerlogic.Journal;
/**
 * A factory that produces a create command.
 * @see taskManagerCreators.commands.Create
 * @version 1.0
 */
public class CreateFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
