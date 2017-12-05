package commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reaction.MailSender;
import reaction.Output;
import reaction.Reaction;
import reaction.Sleep;
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

@Component("create")
public class Create implements Command {

    private Journal journal;

    private String command;

    @Autowired
    public Create(Journal journal) {
        this.journal = journal;
    }

    public Create() {
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(String command) throws ParseException, DataFormatException {
        String name, describe, data;
        List<String> contacts;
        Date dateTime;
        Reaction reaction;

        String[] params = command.split(",");

        name = params[0].substring(params[0].indexOf(" ")).trim();
        describe = params[1].trim();
        dateTime = dateFormat.parse(params[2].trim());
        data = params[3].trim();

        reaction = parseReaction(data);
        contacts = new ArrayList<>();

        for (int i = 4; i < params.length; i++) {
            contacts.add(params[i].trim());
        }

        if (!dateMatcher(dateTime))
            throw new DataFormatException();

        Task task = new Task(name, describe, dateTime, reaction, contacts);

        journal.add(task);
    }

    public Reaction parseReaction(String data) {
        String values[] = data.split("-");
        switch (values[0].trim()) {
            case "sleep":
                return new Sleep(Long.valueOf(values[1].replace(']', ' ').trim()));
            case "output":
                return new Output(values[1].replace(']', ' ').trim());
            case "send":
                return new MailSender();
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean dateMatcher(Date date) {
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(dateFormat.format(date));
        Date now = Date.from(Instant.now());

        if (date.before(now))
            return false;

        return matcher.matches();
    }

    @Override
    public void run() {
        try {
            execute(command);
        } catch (ParseException | DataFormatException e) {
            System.out.println("Arguments error.");
        }
    }
}
