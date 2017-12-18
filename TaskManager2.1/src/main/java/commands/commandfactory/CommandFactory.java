package commands.commandfactory;

import commands.Command;
import taskmanagerlogic.Journal;

public interface CommandFactory {
    Command produceCommand(String[] args , Journal journal);
}
