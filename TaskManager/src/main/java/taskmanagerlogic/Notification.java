package taskmanagerlogic;

import java.util.Date;

/**
 * This is for notification when a task is ready for that.
 * @version 1.0
 */
@Deprecated
public class Notification extends Thread {

    private Task task;

    public Notification(Task task) {
        this.task = task;
    }

    @Override
    synchronized public void run() {
        System.out.println("You`ve got a task!!!!");
        task.setCompletedTime(new Date());
        System.out.println("Name : " + task.getName() + "\nDescribe : " + task.getDescribe() + "\nComplete at " + task.getCompletedTime());
        task.getReaction().perform();
        task.setStatus(taskmanagerlogic.Action.COMPLETED);
    }

    public Task getTask() {
        return task;
    }
}
