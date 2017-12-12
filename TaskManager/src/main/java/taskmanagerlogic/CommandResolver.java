package taskmanagerlogic;

import commands.*;

import java.util.Arrays;

/**
 * Resolver for creating one of the commands.
 * @Version 1.0
 */
public class CommandResolver {
    /**
     * A journal with which commands interact.
     * @see Journal
     */
    private static Journal journal;

    static {
        journal = (Journal) InterAction.context.getBean("journal");
    }

    /**
     * Creates one of several commands.
     * @param commandPart
     * @return Object that implements Command.
     * @see Command
     */
    public static Command createCommand(String[] commandPart) {
        String key;
        key = commandPart[0];
        key = key.substring(0, key.split(" ").length < 2 ? key.length() : key.indexOf(" "));
        switch (key) {
            case "create":
                return new Create(Arrays.toString(commandPart), journal);
            case "show":
                return new Show(commandPart[0], journal);
            case "delete":
                return new Delete(commandPart[0], journal);
            case "clean":
                return new Clean(journal);
            case "help":
                return new Help();
            case "save":
                return new Save(journal);
            case "history":
                return new ShowHistory(journal);
            case "exit":
                return new Exit();
            default:
                throw new IllegalArgumentException("Do not recognized command key");
        }
    }
}
