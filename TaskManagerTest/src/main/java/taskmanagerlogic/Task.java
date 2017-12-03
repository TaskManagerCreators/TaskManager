package taskmanagerlogic;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * JavaBean taskmanagerlogic.Task , describes a  task
 *
 * @version 1.0
 */

@Component
public class Task extends TimerTask implements java.io.Serializable {

    private static final long serialVersionUID = 12321312324L;

    private UUID id;
    private String name;
    private String describe;
    private Date targetTime;
    private Date completedTime;
    private List<String> contacts;


    public UUID getId() {
        return id;
    }


    public Action getStatus() {
        return status;
    }

    public void setStatus(Action status) {
        this.status = status;
    }

    private Action status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Date getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(Date dateTime) {
        this.targetTime = dateTime;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }


    /**
     * Create new object with values
     *
     * @param name
     * @param describe
     * @param dateTime hh.mm (am|pm) (d|dd).(m|mm).yyyy
     * @param contacts
     * @see taskmanagerlogic.UserInterface#datePattern
     */

    public Task(String name, String describe, Date dateTime, List<String> contacts) {
        status = Action.SCHEDULED;
        this.name = name;
        this.describe = describe;
        this.targetTime = dateTime;
        this.contacts = contacts;
        id = UUID.randomUUID();
    }

    public Task() {
        name = "";
        describe = "";
        targetTime = new Date();
        contacts = new ArrayList<>();
        id = null;

    }

    @Override
    public String toString() {
        String taskString = "ID : " + id + "\nStatus : "
                + (status == Action.COMPLETED ? "Completed" :
                (status == Action.RUNNING ? "Running" : "Scheduled"))
                + "\nName : " + name + "\nDescribe : " + describe + "\ntaskmanagerlogic.Task time : " + targetTime;

        if (!contacts.isEmpty()) {
            taskString += "\nContacts : ";
            for (String contact : contacts) {
                taskString += contact + " , ";
            }

            taskString = taskString.substring(0, taskString.lastIndexOf(" ,"));
        }
        return taskString + '\n';
    }

    @Override
    public void run() {
        status = Action.RUNNING;
        Notification notification = new Notification(this);
        notification.start();
        UserInterface userInterface = new UserInterface();
        userInterface.doNext();
    }

    public static final Comparator<Task> COMPARE_BY_TIME = Comparator.comparing(task -> task.targetTime);

}
