package commands.commandfactory;

import commands.Command;
import commands.ShowHistory;
import taskmanagerlogic.Journal;

public class ShowHistoryFactory implements CommandFactory {
    @Override
    public Command produceCommand(Object... args) {
        return new ShowHistory((Journal) args[0]);
    }

    @Override
    public String factoryName() {
        return null;
    }
}
