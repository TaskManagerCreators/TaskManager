package commands.commandfactory;

import commands.Command;

public interface CommandFactory {
    Command produceCommand(Object... args);
    String factoryName();
}
