package taskmanagerlogic;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * JavaBean Task , describes a  task
 *
 * @version 1.0
 */

public class Task implements java.io.Serializable {

    private static final long serialVersionUID = 12321312324L;

    public UUID getId() {
        return id;
    }

    private UUID id;
    private String name;
    private String describe;
    private Date dateTime;
    private List<String> contacts;
    private boolean executed = false;

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


    /**
     * Create new object with values
     *
     * @param name
     * @param describe
     * @param dateTime hh.mm (am|pm) (d|dd).(m|mm).yyyy
     * @param contacts
     * @see UserInterface#datePattern
     * @see Task#Task()
     */

    public Task(String name, String describe, Date dateTime, List<String> contacts) {
        this.name = name;
        this.describe = describe;
        this.dateTime = dateTime;
        this.contacts = contacts;
        id = UUID.randomUUID();
    }

    public Task() {
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }


    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }


    @Override
    public String toString() {
        String taskString = "ID : " + id + "\nStatus : " + (executed == true ? "Executed" : "Waiting for executing") + "\nName : " + name + "\nDescribe : " + describe + "\nTask time : " + dateTime + "\nContacts : ";

        for (String contact : contacts) {
            taskString += contact + " , ";
        }

        taskString = taskString.substring(0, taskString.lastIndexOf(" ,"));

        return taskString + '\n';
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
