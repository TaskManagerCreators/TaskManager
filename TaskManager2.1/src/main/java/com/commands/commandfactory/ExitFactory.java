package com.commands.commandfactory;

import com.commands.Command;
import com.commands.Exit;

public class ExitFactory implements CommandFactory {
    @Override
    public Command produceCommand(String ... args) {
        return new Exit();
    }

    @Override
    public String factoryName() {
        return null;
    }
}
