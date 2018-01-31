package com.taskmanagerlogic;

import com.InterAction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.reaction.Reaction;
import com.repositories.ErrorRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * JavaBean Task , describes a  task
 *
 * @version 1.0
 */
@Component
@ComponentScan(basePackages = {"com"})
@Document(collection = "tasks")
public class Task implements Serializable, Runnable {

    private static final long serialVersionUID = 12321312324L;

    @Id
    private UUID id;
    private String name;
    private String describe;
    private int size;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private Date targetTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private Date completedTime;

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    private Reaction reaction;

    private List<String> contacts;

    private ErrorRepository errorRecordRepository;

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

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public Task() {
        id = UUID.randomUUID();
    }

    @Override
    public String toString() {

        String taskString = "";
        taskString = "ID : " + id + "\nStatus : " + status.name()
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


    @Override
    public void run() {
        errorRecordRepository = (ErrorRepository) InterAction.applicationContext.getBean("error_record");
        status = Action.RUNNING;
        System.out.println("You`ve got a task!!!!");
        this.setCompletedTime(new Date());
        System.out.println("Name : " + this.getName() + "\nDescribe : " + this.getDescribe() + "\nComplete at " + this.getCompletedTime());
        try {
            reaction.perform();
        } catch (Exception e) {
            System.out.println("Еггог");
            ErrorRecord errorRecord = new ErrorRecord(
                    this.id,
                    e.getClass().getName(),
                    "Some problem while " + reaction.getType().name(),
                    e.getMessage(),
                    new Date()
            );
            errorRecordRepository.insert(errorRecord);
        }
        this.setStatus(Action.COMPLETED);
    }


}
