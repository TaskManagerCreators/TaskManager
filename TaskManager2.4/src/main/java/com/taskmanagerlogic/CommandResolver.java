package com.taskmanagerlogic;

import com.commands.Command;
import com.commands.commandfactory.*;

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

    private final static Map<String, CommandFactory> keeper = new HashMap<String, CommandFactory>() {{
        put("create", new CreateFactory());
        put("help", new HelpFactory());
        put("delete", new DeleteFactory());
        put("show", new ShowFactory());
        put("exit", new ExitFactory());
        put("history", new ShowHistoryFactory());
        put("clean", new CleanFactory());

    }};


    /**
     * Creates one of several com.commands.
     *
     * @return Object that implements Command.
     * @see Command
     */
    public static Command createCommand(String[] commandPart) {
        String key;
        key = commandPart[0];
        key = key.substring(0, key.split(" ").length < 2 ? key.length() : key.indexOf(" "));

        if (commandPart.length > 1)
            commandPart[0] = commandPart[0].substring(key.length() + 1, commandPart[0].length());


        CommandFactory factory = new CommandResolver().keeper.get(key);

        if (factory == null)
            throw new IllegalArgumentException("Do not recognized command key.");

        return factory.produceCommand(commandPart);
    }
}
