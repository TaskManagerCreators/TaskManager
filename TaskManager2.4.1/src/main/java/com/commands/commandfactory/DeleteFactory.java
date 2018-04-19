package com.commands.commandfactory;

import com.commands.Command;
import com.commands.Delete;

public class DeleteFactory implements CommandFactory {
    @Override
    public Command produceCommand(String ... args) {
        return new Delete(args);
    }

    @Override
    public String factoryName() {
        return null;
    }
}
