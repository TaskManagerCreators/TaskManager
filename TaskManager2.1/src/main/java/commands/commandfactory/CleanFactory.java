package commands.commandfactory;

import commands.Clean;
import commands.Command;
import commands.Create;
import taskmanagerlogic.Journal;

public class CleanFactory implements CommandFactory {
    @Override
    public Command produceCommand(Object... args) {
        return new Clean((Journal)args[0]);
    }

    @Override
    public String factoryName() {
        return "Clean";
    }
}
