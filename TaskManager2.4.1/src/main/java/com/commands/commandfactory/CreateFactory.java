package com.commands.commandfactory;

import com.commands.Command;
import com.commands.Create;

public class CreateFactory implements CommandFactory {
    @Override
    public Command produceCommand(String ...  args) {
        return new Create(args);
    }

    @Override
    public String factoryName() {
        return null;
    }
}
