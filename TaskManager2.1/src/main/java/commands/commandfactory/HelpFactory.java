package commands.commandfactory;

import commands.Command;
import taskmanagerlogic.Journal;

public class HelpFactory implements CommandFactory {
    @Override
    public Command produceCommand(String[] args , Journal journal) {
        return null;
    }
}
