package commands.commandfactory;

import commands.Command;
import commands.Show;
import taskmanagerlogic.Journal;

public class ShowFactory implements CommandFactory {
    @Override
    public Command produceCommand(Object... args) {
        return new Show((String) args[0], (Journal) args[1]);
    }

    @Override
    public String factoryName() {
        return null;
    }
}
