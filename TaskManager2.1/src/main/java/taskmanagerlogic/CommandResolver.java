package taskmanagerlogic;

import commands.Command;
import commands.commandfactory.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Resolver for creating one of the commands.
 *
 * @Version 1.0
 */
public class CommandResolver {
    /**
     * A journal with which commands interact.
     *
     * @see Journal
     */
    private static Journal journal;

    static {
        //journal = (Journal) InterAction.context.getBean("journal");
    }


    @Autowired
    private CommandFactory commandFactory;

    private static final Map<String , String[]> keeper = new HashMap<String, String[]>(){{
        put("create" , new String[]{"commandPart" , "journal"});
        put("help" , null);

    }};

    /**
     * Creates one of several commands.
     *
     * @param commandPart
     * @return Object that implements Command.
     * @see Command
     */
    public  Command createCommand(String[] commandPart) {
        String key;
        key = commandPart[0];
        key = key.substring(0, key.split(" ").length < 2 ? key.length() : key.indexOf(" "));
        switch (key) {
            case "create":
                StringBuilder builder = new StringBuilder();
                for (String s : commandPart) {
                    builder.append(s + ',');
                }
                return new CreateFactory().produceCommand(builder.substring(0, builder.lastIndexOf(",")), journal);
            case "show":
                return new ShowFactory().produceCommand(commandPart[0], journal);
            case "delete":
                return new DeleteFactory().produceCommand(commandPart[0], journal);
            case "clean":
                return new CleanFactory().produceCommand(journal);
            case "help":
                return new HelpFactory().produceCommand();
            case "save":
                return new SaveFactory().produceCommand(journal);
            case "history":
                return new ShowHistoryFactory().produceCommand(journal);
            case "exit":
                return new ExitFactory().produceCommand();
            default:
                throw new IllegalArgumentException("Do not recognized command key.");
        }
    }
}
