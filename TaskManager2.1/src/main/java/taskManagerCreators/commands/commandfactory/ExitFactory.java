package taskManagerCreators.commands.commandfactory;

import taskManagerCreators.commands.Command;
import taskManagerCreators.taskmanagerlogic.Journal;
/**
 * A factory that produces an exit command.
 * @see taskManagerCreators.commands.Exit
 * @version 1.0
 */
public class ExitFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
