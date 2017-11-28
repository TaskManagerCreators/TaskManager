package taskmanagerlogic;

import java.io.*;
import java.util.*;

/**
 * This class is the history of completed tasks.
 * It works with the history.txt file.
 *
 * @Version 1.0
 */
public class History {
    /**
     * A list that contains cleaned tasks.
     * It has not more than 20 latest tasks.
     */
    private Set<Task> cleanedTasks = new HashSet<>();
    public File file = new File(fileName);
    private static final String fileName = "history.txt";

    public Set<Task> getCleanedTasks() {
        return cleanedTasks;
    }

    public void setCleanedTasks(HashSet<Task> cleanedTasks) {
        this.cleanedTasks = cleanedTasks;
    }

    public History() {
        load();
    }

    @Override
    public String toString() {
        String result;
        result = "Count of deleted tasks : " + cleanedTasks.size();
        for (Task task : cleanedTasks) {
            result += "\nName : " + task.getName();
            result += "\nDate : " + task.getTargetTime().toString();
        }
        return result;
    }

    /**
     * This method adds a completed task into the history list.
     *
     * @param task
     */
    public void addCleanedTask(Task task) {
        cleanedTasks.add(task);
        if (cleanedTasks.size() == 21)
            cleanedTasks.remove(0);
    }

    public void load() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file.length() > 0) {
            try (FileInputStream input = new FileInputStream(file);
                 ObjectInputStream inputObject = new ObjectInputStream(input)) {
                Task task;
                while (input.available() > 0) {
                    task = (Task) inputObject.readObject();
                    cleanedTasks.add(task);
                }
                input.close();
                inputObject.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            cleanedTasks = new HashSet<>();
        }
    }

    public void save() {
        try (FileOutputStream output = new FileOutputStream(file);
             ObjectOutput outputObject = new ObjectOutputStream(output)) {
            for (Task task : cleanedTasks) {
                outputObject.writeObject(task);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
