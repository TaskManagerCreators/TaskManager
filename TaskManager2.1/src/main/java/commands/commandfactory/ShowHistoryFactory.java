package commands.commandfactory;

import commands.Command;
import commands.ShowHistory;
import taskmanagerlogic.Journal;
/**
 * A factory that produces a command to show the history.
 * @see commands.ShowHistory
 * @version 1.0
 */
public class ShowHistoryFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return new ShowHistory(journal);
    }
}
