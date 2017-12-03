package taskmanagerlogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@EnableScheduling
@Component
@ComponentScan("taskmanagerlogic")
public class Cleaner {

    private long deltaOfTime;

    //@Autowired
    private volatile UserInterface ui;

    public taskmanagerlogic.Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(taskmanagerlogic.Task currentTask) {
        this.currentTask = currentTask;
    }

    private taskmanagerlogic.Task currentTask;


    @Autowired
    public Cleaner(UserInterface ui) {
        this.ui = ui;
        this.deltaOfTime = 0;
    }

    public long getDeltaOfTime() {
        return deltaOfTime;
    }

    public void setDeltaOfTime(long deltaOfTime) {
        this.deltaOfTime = deltaOfTime;
    }

    @Scheduled(fixedDelay = 60000)
    public void clean() {
        currentTask = ui.getCurrentTask();
        if (currentTask != null && currentTask.getStatus() == Action.COMPLETED) {
            long range = Date.from(Instant.now()).getTime() - currentTask.getCompletedTime().getTime();
            if (range - 1000 <= deltaOfTime && deltaOfTime <= range + 1000) {
                ui.getJournal().delete(currentTask.getId());
                //ui.getJournal().getHistory().addCleanedTask(currentTask);
            }
        }
        System.out.println("Hey");
        // System.out.println(ui);
        //System.out.println(ui.getJournal());
    }
}

