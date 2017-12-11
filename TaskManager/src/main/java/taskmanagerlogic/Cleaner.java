package taskmanagerlogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * Used for delete completed tasks
 * @version 2.0
 */
@EnableScheduling
@Component
@ComponentScan("taskmanagerlogic")
public class Cleaner {

    private Journal journal;

    @Autowired
    public Cleaner(Journal journal) {
        this.journal = journal;

    }

    @Scheduled(fixedDelay = 5000)
    private void clean() {
        if (!journal.getTasks().isEmpty()) {
            for (Iterator<Task> iterator = journal.getTasks().iterator(); iterator.hasNext(); ) {
                Task task = iterator.next();
                if (task != null && task.getStatus() == Action.COMPLETED) {
                    iterator.remove();
                    journal.getHistory().addCleanedTask(task);
                    System.out.println("Deleted with id : " + task.getId() + '.');
                }
            }
        }
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }
}

