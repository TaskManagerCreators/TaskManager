package taskManagerCreators.commands.commandfactory;

import taskManagerCreators.commands.Command;
import taskManagerCreators.commands.ShowHistory;
import taskManagerCreators.taskmanagerlogic.Journal;
/**
 * A factory that produces a command to show the history.
 * @see taskManagerCreators.commands.ShowHistory
 * @version 1.0
 */
public class ShowHistoryFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return new ShowHistory(journal);
    }
}
