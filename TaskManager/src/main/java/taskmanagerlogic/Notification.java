package taskmanagerlogic;

import java.util.Date;

public class Notification extends Thread {

    private  Task task;

    public Notification(Task task) {
        this.task = task;
    }

    @Override
    synchronized public void run() {
        System.out.println("You`ve got a task!!!!");
        task.setCompletedTime(new Date());
        System.out.println("Name : " + task.getName() + "\nDescribe : " + task.getDescribe() + "\nComplete at " + task.getCompletedTime());
        task.setStatus(taskmanagerlogic.Action.COMPLETED);
        task.getReaction().perform();
    }

    public Task getTask() {
        return task;
    }
}
