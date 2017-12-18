package commands.commandfactory;

import commands.Command;
import commands.ShowHistory;
import taskmanagerlogic.Journal;

public class ShowHistoryFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return new ShowHistory(journal);
    }
}
