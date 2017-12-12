package commands;

import taskmanagerlogic.Journal;

import java.io.IOException;
import java.text.ParseException;
import java.util.zip.DataFormatException;

public class ShowHistory implements Command {
    private Journal journal;

    public ShowHistory(Journal journal) {
        this.journal = journal;
    }

    @Override
    public void execute(String command) throws ParseException, DataFormatException, IOException {
        System.out.println(journal.getHistory());
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
