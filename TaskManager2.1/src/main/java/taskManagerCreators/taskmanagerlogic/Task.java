package taskManagerCreators.taskmanagerlogic;

//import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import taskManagerCreators.reaction.Reaction;
import taskManagerCreators.commands.Command;
import taskManagerCreators.reaction.ReactionType;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * JavaBean Task , describes a  task
 *
 * @version 1.1
 */
@Component
@Document(collection = "tasks")
public class Task implements Serializable, Runnable {

    private static final long serialVersionUID = 12321312324L;

    @Id
    private UUID id;
    private String name;
    private String describe;
    private Date targetTime;
    private Date completedTime;
    private Reaction reaction;
    private List<String> contacts;
    private ErrorRecord errorRecord;

    public UUID getId() {
        return id;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
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

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public List<String> getContacts() {
        return contacts;
    }

    /**
     * Create new object with values
     *
     * @param name
     * @param describe
     * @param dateTime hh.mm (am|pm) (d|dd).(m|mm).yyyy
     * @param reaction
     * @param contacts
     * @see Reaction
     */
    public Task(String name, String describe, Date dateTime, Reaction reaction, List<String> contacts) {
        status = Action.SCHEDULED;
        this.name = name;
        this.describe = describe;
        this.targetTime = dateTime;
        this.reaction = reaction;
        this.contacts = contacts;
        id = UUID.randomUUID();
    }

    public Task(String name, String describe, Date dateTime, List<String> contacts) {
        status = Action.SCHEDULED;
        this.name = name;
        this.describe = describe;
        this.targetTime = dateTime;
        this.contacts = contacts;
        id = UUID.randomUUID();
    }


    public Task() {
    }

    @Override
    public String toString() {
        String taskString="";
                taskString = "ID : " + id + "\nStatus : "
                    + (status == Action.COMPLETED ? "Completed" :
                    (status == Action.RUNNING ? "Running" : "Scheduled"))
                    + "\nName : " + name + "\nDescribe : " + describe + "\nTask time : " + targetTime;

            if (reaction != null) {
                taskString += "\nReaction (type , value) : (" + reaction.getType().toString() +
                        " , " + reaction.getValue().toString() + ')';
            }

            if (!contacts.isEmpty()) {
                taskString += "\nContacts : ";
                for (String contact : contacts) {
                    taskString += contact + " , ";
                }

                taskString = taskString.substring(0, taskString.lastIndexOf(" ,"));
            }

        return taskString + '\n';
    }

    /**
     * This runs when the task is ready for an action.
     */
    @Override
    public void run() {
        status = Action.RUNNING;
        System.out.println("You`ve got a task!!!!");
        this.setCompletedTime(new Date());
        System.out.println("Name : " + this.getName() + "\nDescribe : " + this.getDescribe() + "\nComplete at " + this.getCompletedTime());
        try {
            this.getReaction().perform();
        }
        catch (Exception e){
            String message = "Some problem while ";
            if(reaction.getType() == ReactionType.OUTPUT){
                message += "printing.";
            }
            else if(reaction.getType() == ReactionType.SLEEP){
                message += "sleeping.";
            }
            else{
                message += "sending email";
            }
            this.errorRecord = new ErrorRecord(this.id , e.getClass() , message , e.getStackTrace() , new Date() );
        }
        this.setStatus(Action.COMPLETED);

    }

    public static final Comparator<Task> COMPARE_BY_TIME = Comparator.comparing(task -> task.targetTime);

}
