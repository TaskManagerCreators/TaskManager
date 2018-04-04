package com.commands.commandfactory;

import com.commands.Command;

public interface CommandFactory {
    Command produceCommand(String ... args);
    String factoryName();
}
