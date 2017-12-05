package taskmanagerlogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.DataFormatException;


/**
 * Includes methods for interacts with ending users
 *
 * @version 1.0
 */
@Component
public class UserInterface {

    public Journal getJournal() {
        return journal;
    }

    @Autowired
    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    private Journal journal;

    public String getDatePattern() {
        return datePattern;
    }

    /**
     * Pattern of tasks date-field
     */
    private String datePattern = "^((([1 - 12]):(((0[0-9])|([1-5][0-9]))) ((am)|(pm)))|((0?[0-9]|1[0-9]|2[0-3]):((0[0-9])|([1-5][0-9])))) ((0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).(20\\d\\d))$";

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     *
     */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");

    /**
     * Constant for deleting a task
     */
    protected static final int DELETE = 0;

    /**
     * Constant for searching a task
     */
    protected static final int SHOW = 1;



    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    /**
     * The next task that must be notified
     */
    private Task currentTask;


    public static void help() {
        String result = "Commands: \ncreate [name , describe , time , contacts] - create task\n";
        result += "delete [index | time] - delete task by index or  time\n";
        result += "Other commands : show , clean , save , exit.";
        System.out.println(result);
    }

    /**
     * It schedules a task using Scheduler from Journal.
     * @param task
     */
    public void schedule(Task task){
        Date date = new Date();
        if(date.getTime() - task.getTargetTime().getTime() < 0)
            journal.getScheduler().schedule(task,task.getTargetTime());
    }

    /**
     * It schedules all tasks from journal.
     * Mostly it`s used at the 1`st time after loading.
     */
    public void schedule(){
        Date date = new Date();
        for(Task t : journal.getTasks()){
            if(date.getTime() - t.getTargetTime().getTime() < 0)
                journal.getScheduler().schedule(t,t.getTargetTime());
        }
    }

    public UserInterface() {
        journal = new Journal();
        journal.load(new File("journal.txt"));
    }



}
