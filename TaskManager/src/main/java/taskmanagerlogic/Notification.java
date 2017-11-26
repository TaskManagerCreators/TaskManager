package taskmanagerlogic;

public class Notification extends Thread {
    private Task task;
    public Notification(Task task){
        this.task = task;
    }
    @Override
    public void run(){
        System.out.println("You`ve got a task!!!!");
        System.out.println(task.getName()+" "+task.getDescribe());
        task.setStatus(Action.COMPLETED);

    }
    public Task getTask(){
        return task;
    }
}
