package commands.commandfactory;

import commands.Command;
import commands.Help;

public class HelpFactory implements CommandFactory {
    @Override
    public Command produceCommand(Object... args) {
        return new Help();
    }

    @Override
    public String factoryName() {
        return null;
    }
}
