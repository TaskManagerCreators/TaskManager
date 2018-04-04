package com.commands.commandfactory;

import com.commands.Command;
import com.commands.ShowHistory;

public class ShowHistoryFactory implements CommandFactory {
    @Override
    public Command produceCommand(String ... args) {
        return new ShowHistory();
    }

    @Override
    public String factoryName() {
        return null;
    }
}
