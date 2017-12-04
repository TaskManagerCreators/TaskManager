package taskmanagerlogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@EnableScheduling
@Component
@ComponentScan("taskmanagerlogic")
public class Cleaner {

    public UserInterface getUi() {
        return ui;
    }

    public void setUi(UserInterface ui) {
        this.ui = ui;
    }

    private UserInterface ui;


    @Autowired
    public Cleaner(UserInterface userInterface) {
        this.ui = userInterface;

    }

    @Scheduled(fixedDelay = 5000)
    private void clean() {
        if (!ui.getJournal().getTasks().isEmpty()) {
            for (Iterator<Task> iterator = ui.getJournal().getTasks().iterator(); iterator.hasNext(); ) {
                Task task = iterator.next();
                if (task != null && task.getStatus() == Action.COMPLETED) {
                    iterator.remove();
                    ui.getJournal().getHistory().addCleanedTask(task);
                    System.out.println("deleted");
                }
            }
        }
    }
}

