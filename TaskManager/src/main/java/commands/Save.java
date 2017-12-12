package commands;

import taskmanagerlogic.Journal;

import java.io.IOException;
import java.text.ParseException;
import java.util.zip.DataFormatException;

public class Save implements Command {

    private Journal journal;

    public Save(Journal journal) {
        this.journal = journal;
    }

    @Override
    public void execute(String command) throws ParseException, DataFormatException, IOException {
        journal.save();
        System.out.println("Command 'save' executed successfully.");
    }

    @Override
    public void run() {
        try {
            execute(null);
        } catch (ParseException | DataFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
