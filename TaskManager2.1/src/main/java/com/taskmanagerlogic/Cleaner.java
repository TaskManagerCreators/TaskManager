package com.taskmanagerlogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * Used for delete completed tasks
 *
 * @version 2.0
 */
@EnableScheduling
@Component
public class Cleaner {

    private Journal journal;

    private History history = new History();

    @Autowired
    public Cleaner(Journal journal) {
        this.journal = journal;
    }

    @Scheduled(fixedDelay = 5000 * 10)
    private void clean() {
        if (!journal.getTasks().isEmpty()) {
            System.out.println("Hey - hey");
            for (Iterator<Task> iterator = journal.getTasks().iterator(); iterator.hasNext();) {
                Task task = iterator.next();
                if (task != null && task.getStatus() == Action.COMPLETED) {
                    iterator.remove();
                    journal.delete(task.getId());
                    history.addCleanedTask(task);
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

