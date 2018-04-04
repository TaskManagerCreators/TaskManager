package com.commands.commandfactory;

import com.commands.Clean;
import com.commands.Command;

public class CleanFactory implements CommandFactory {
    @Override
    public Command produceCommand(String ... args) {
        return new Clean();
    }

    @Override
    public String factoryName() {
        return "clean";
    }
}
