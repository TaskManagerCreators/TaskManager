package taskmanagerlogic;

import java.time.Instant;
import java.util.Date;

public class Cleaner implements Runnable {

    private long deltaOfTime;
    private UserInterface ui;

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    private Task currentTask;


    public Cleaner(UserInterface ui, long deltaOfTime) {
        this.ui = ui;
        this.deltaOfTime = deltaOfTime;
    }

    @Override
    public void run() {
        currentTask = ui.getCurrentTask();
        if (currentTask != null && currentTask.getStatus() == Action.COMPLETED) {
             /*&&
                    (Date.from(Instant.now()).getTime() - task.getCompletedTime().getTime() == deltaOfTime)*/
            ui.getJournal().delete(ui.getCurrentTask().getId());
            ui.getJournal().getHistory().addCleanedTask(currentTask);
        }

        System.out.println("Я работаю");
    }
}

