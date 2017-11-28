package taskmanagerlogic;

import java.time.Instant;
import java.util.Date;

public class Cleaner extends Thread {

    private long deltaOfTime;

    private volatile UserInterface ui;

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    private Task currentTask;


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

    @Override
    public void run() {
        while (true) {
            currentTask = ui.getCurrentTask();
            if (currentTask != null && currentTask.getStatus() == Action.COMPLETED &&
                    Date.from(Instant.now()).getTime() - currentTask.getCompletedTime().getTime() >= deltaOfTime) {
                ui.getJournal().delete(currentTask.getId());
                ui.getJournal().getHistory().addCleanedTask(currentTask);
            }

        }
    }
}

