package taskmanagerlogic;

import commands.*;

import java.util.Arrays;

public class CommandResolver {

    private static Journal journal;

    static {
        journal = (Journal) InterAction.context.getBean("journal");
    }

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
            default:
                throw new IllegalArgumentException("Do not recognized command key");
        }
    }
}
