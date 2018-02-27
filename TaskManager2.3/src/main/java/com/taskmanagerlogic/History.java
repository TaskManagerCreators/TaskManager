package com.taskmanagerlogic;

import com.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * This class is the history of completed tasks.
 *
 * @version 1.0
 */
@Component
@Document(collection = "history")
public class History {

    private Date lastCall;

    private Task currentTask;

    public History() {

    }

    public History(Task task) {
        currentTask = task;
    }

    @Autowired
    private HistoryRepository historyRepository;

    public Date getLastCall() {
        return lastCall;
    }

    @Override
    public String toString() {
        String result;
        result = "Count of deleted tasks : " + countOfCleanedTask();
        if (lastCall != null)
            result += "\nLast call : " + lastCall;
        else
            result += "\nLast call : unknown";

        return result;
    }


    public void addCleanedTask(Task task) {
        if (countOfCleanedTask() == 20) {
            historyRepository.deleteAll();
        }
        historyRepository.insert(new History(task));
        lastCall = new Date();
    }



    private long countOfCleanedTask() {
        return historyRepository.count();
    }

}
