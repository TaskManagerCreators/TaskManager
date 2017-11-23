package taskmanagerlogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

/**
 * JavaBean Task , describes a  task
 *
 * @version 1.0
 */

public class Task extends TimerTask implements java.io.Serializable {

    private static final long serialVersionUID = 12321312324L;


    public UUID getId() {
        return id;
    }

    private UUID id;
    private String name;
    private String describe;
    private Date dateTime;
    private List<String> contacts;

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


    /**
     * Create new object with values
     *
     * @param name
     * @param describe
     * @param dateTime hh.mm (am|pm) (d|dd).(m|mm).yyyy
     * @param contacts
     * @see UserInterface#datePattern
     */

    public Task(String name, String describe, Date dateTime, List<String> contacts) {
        status = Action.SCHEDULED;
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
        String taskString = "ID : " + id + "\nStatus : "
                + (status == Action.COMPLETED ? "Completed" :
                (status == Action.RUNNING ? "Running" : "Scheduled"))
                + "\nName : " + name + "\nDescribe : " + describe + "\nTask time : " + dateTime;

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
        UserInterface.doNext();
    }

    public static final Comparator<Task> COMPARE_BY_TIME = Comparator.comparing(task -> task.dateTime);


}
