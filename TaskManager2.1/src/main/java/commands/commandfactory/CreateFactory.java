package commands.commandfactory;

import commands.Command;
import commands.Create;
import taskmanagerlogic.Journal;

public class CreateFactory implements CommandFactory {
    @Override
    public Command produceCommand(Object... args) {
        return new Create((String) args[0], (Journal) args[1]);
    }

    @Override
    public String factoryName() {
        return null;
    }
}
