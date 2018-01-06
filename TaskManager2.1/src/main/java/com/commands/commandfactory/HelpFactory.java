package com.commands.commandfactory;

import com.commands.Command;
import com.commands.Help;

public class HelpFactory implements CommandFactory {
    @Override
    public Command produceCommand(String ... args) {
        return new Help();
    }

    @Override
    public String factoryName() {
        return null;
    }
}
