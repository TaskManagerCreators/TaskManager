package commands;

import org.springframework.beans.factory.annotation.Autowired;
import taskmanagerlogic.Journal;
import taskmanagerlogic.Task;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class Create implements Command {

    private Journal journal;

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    //@Autowired
    public Create(Journal journal) {
        this.journal = journal;
    }

    @Override
    public void execute(String command) throws ParseException, DataFormatException {
        String name, describe;
        List<String> contacts;
        Date dateTime;

        String[] params = command.split(",");

        name = params[0].substring(params[0].indexOf(" ")).trim();
        describe = params[1].trim();
        dateTime = dateFormat.parse(params[2].trim());

        contacts = new ArrayList<>();


        for (int i = 3; i < params.length; i++) {
            contacts.add(params[i].trim());
        }

        if (!dateMatcher(dateTime))
            throw new DataFormatException();

        Task task = new Task(name, describe, dateTime, contacts);

        journal.add(task);
    }

    public boolean dateMatcher(Date date) {
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(dateFormat.format(date));
        Date now = Date.from(Instant.now());

        if (date.before(now))
            return false;

        return matcher.matches();
    }
}
