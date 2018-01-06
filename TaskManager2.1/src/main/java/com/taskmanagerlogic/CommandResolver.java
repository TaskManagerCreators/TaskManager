package com.taskmanagerlogic;

import com.commands.Command;
import com.commands.commandfactory.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Resolver for creating one of the com.commands.
 *
 * @Version 1.0
 */
public class CommandResolver {
    /**
     * A journal with which com.commands interact.
     *
     * @see Journal
     */

    @Autowired
    private CommandFactory commandFactory;

    private static final Map<String, String[]> keeper = new HashMap<String, String[]>() {{
        put("create", new String[]{"commandPart", "journal"});
        put("help", null);

    }};

    /**
     * Creates one of several com.commands.
     *
     * @param commandPart
     * @return Object that implements Command.
     * @see Command
     */
    public static Command createCommand(String[] commandPart) {
        String key;
        key = commandPart[0];
        key = key.substring(0, key.split(" ").length < 2 ? key.length() : key.indexOf(" "));

        if (commandPart.length > 1)
            commandPart[0] = commandPart[0].substring(key.length() + 1, commandPart[0].length());

        switch (key) {
            case "create":
                return new CreateFactory().produceCommand(commandPart);
            case "show":
                return new ShowFactory().produceCommand(commandPart);
            case "delete":
                return new DeleteFactory().produceCommand(commandPart);
            case "clean":
                return new CleanFactory().produceCommand(commandPart);
            case "help":
                return new HelpFactory().produceCommand();
            case "history":
                return new ShowHistoryFactory().produceCommand(commandPart);
            case "exit":
                return new ExitFactory().produceCommand();
            default:
                throw new IllegalArgumentException("Do not recognized command key.");
        }
    }
}
