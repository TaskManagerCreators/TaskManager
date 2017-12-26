package commands.commandfactory;

import commands.Command;
import commands.Delete;
import taskmanagerlogic.Journal;

public class DeleteFactory implements CommandFactory {
    @Override
    public Command produceCommand(Object... args) {
        return new Delete((String) args[0], (Journal) args[1]);
    }

    @Override
    public String factoryName() {
        return null;
    }
}
