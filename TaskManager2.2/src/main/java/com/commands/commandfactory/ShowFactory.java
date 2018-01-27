package com.commands.commandfactory;

import com.commands.Command;
import com.commands.Show;

public class ShowFactory implements CommandFactory {
    @Override
    public Command produceCommand(String ... args) {
        return new Show(args);
    }

    @Override
    public String factoryName() {
        return null;
    }
}
