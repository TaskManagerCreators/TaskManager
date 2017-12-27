package taskManagerCreators.commands.commandfactory;

import taskManagerCreators.commands.Command;
import taskManagerCreators.taskmanagerlogic.Journal;
/**
 * A factory that produces a save command.
 * @see taskManagerCreators.commands.Save
 * @version 1.0
 */
public class SaveFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
