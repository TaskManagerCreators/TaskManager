package commands.commandfactory;

import commands.Command;
import taskmanagerlogic.Journal;

/**
 * An interface for each command factory.
 * @see CleanFactory
 * @see CreateFactory
 * @see DeleteFactory
 * @see ExitFactory
 * @see HelpFactory
 * @see SaveFactory
 * @see ShowFactory
 * @see ShowHistoryFactory
 */
public interface CommandFactory {
    Command produceCommand(String[] args , Journal journal);
}
