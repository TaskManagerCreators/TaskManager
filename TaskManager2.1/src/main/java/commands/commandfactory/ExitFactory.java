package commands.commandfactory;

import commands.Command;
import commands.Exit;
import taskmanagerlogic.Journal;

public class ExitFactory implements CommandFactory {
    @Override
    public Command produceCommand(Object... args) {
        return new Exit();
    }

    @Override
    public String factoryName() {
        return null;
    }
}
