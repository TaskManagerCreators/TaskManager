package taskmanagerlogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.DataFormatException;


/**
 * Includes methods for interacts with ending users
 *
 * @version 1.0
 */
@Component
public class UserInterface {

    public Journal getJournal() {
        return journal;
    }

    @Autowired
    public void setJournal(Journal journal) {
        this.journal = journal;
        journal.load(new File("journal.txt"));
    }

    private Journal journal;

    public static void help() {
        String result = "Commands: \ncreate [name , describe , time , contacts] - create task\n";
        result += "delete [index | time] - delete task by index or  time\n";
        result += "Other commands : show , clean , save , exit.";
        System.out.println(result);
    }

    public void schedule(Task task) {
        Date date = new Date();
        if (date.getTime() - task.getTargetTime().getTime() < 0)
            journal.getScheduler().schedule(task, task.getTargetTime());
    }

    public void schedule() {
        Date date = new Date();
        for (Task t : journal.getTasks()) {
            if (date.getTime() - t.getTargetTime().getTime() < 0)
                journal.getScheduler().schedule(t, t.getTargetTime());
        }
    }

    public UserInterface() {
        journal = new Journal();
        journal.load(new File("journal.txt"));
    }


}
