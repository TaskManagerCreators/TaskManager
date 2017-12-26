package commands.commandfactory;

import commands.Command;
import commands.Save;
import taskmanagerlogic.Journal;

public class SaveFactory implements CommandFactory {
    @Override
    public Command produceCommand(Object... args) {
        return new Save((Journal) args[0]);
    }

    @Override
    public String factoryName() {
        return null;
    }
}
