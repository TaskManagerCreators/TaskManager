package taskmanagerlogic;

import java.util.Date;

public class Notification extends Thread {

    private volatile taskmanagerlogic.Task task;

    public Notification(taskmanagerlogic.Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        System.out.println("You`ve got a task!!!!");
        task.setCompletedTime(new Date());
        System.out.println("Name : " + task.getName() + "\nDescribe : " + task.getDescribe() + "\nComplete at " + task.getCompletedTime());
        task.setStatus(taskmanagerlogic.Action.COMPLETED);
    }

    public taskmanagerlogic.Task getTask() {
        return task;
    }
}
