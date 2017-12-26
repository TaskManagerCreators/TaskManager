package taskmanagerlogic;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is the history of completed tasks.
 *
 * @version 1.0
 */
@Component("history")
@Document(collection = "history")
public class History {

    private Date lastCall;

    private List<Task> cleanedTasks;

    public History() {
        cleanedTasks = new ArrayList<>();
    }

    public List<Task> getCleanedTasks() {
        return cleanedTasks;
    }

    public Date getLastCall() {
        return lastCall;
    }

    @Override
    public String toString() {
        String result;
        result = "Count of deleted tasks : " + cleanedTasks.size();
        if (lastCall != null)
            result += "\nLast call : " + lastCall;
        else
            result += "\nLast call : unknown";

        return result;
    }


    public void addCleanedTask(Task task) {
        if (cleanedTasks.size() == 20)
            cleanedTasks.remove(0);

        cleanedTasks.add(task);
        lastCall = new Date();
    }

}
