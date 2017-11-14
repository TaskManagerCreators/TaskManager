package taskmanagerlogic;

/**
 JavaBean Task , describes a  task
 @version 1.0
 */

public class Task implements java.io.Serializable {

    private String name;
    private String describe;
    private String dateTime;
    private String contacts;

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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * Create new object with values
     * @param name
     * @param describe
     * @param dateTime hh.mm (am|pm) (d|dd).(m|mm).yyyy
     * @see UserInterface#datePattern
     * @param contacts
     * @see Task#Task()
     */

    public Task(String name, String describe, String dateTime, String contacts) {
        this.name = name;
        this.describe = describe;
        this.dateTime = dateTime;
        this.contacts = contacts;
    }

    public Task() {
    }
}
