package taskmanagerlogic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Class Journal describes journal which contains tasks and works with journal.txt file
 *
 * @version 1.0
 */

public class Journal {

    private List<Task> tasks;

    /**
     * Object of Journal class 0 singleton object
     */
    private static Journal ourInstance = new Journal();

    /**
     * Contains serializable tasks
     */
    private File journal;

    public static Journal getInstance() {
        return ourInstance;
    }

    /**
     * Create once new object and create journal file , if it does not exist
     * Fills a collection a collection of tasks , if file is empty
     */
    private Journal() {
        File file = new File("journal.txt");
        journal = file;
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
                tasks = new ArrayList<>();
                while (input.available() > 0) {
                    task = (Task) inputObject.readObject();
                    tasks.add(task);
                }
                input.close();
                inputObject.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            tasks = new ArrayList<>();
        }
    }

    public void save() throws IOException {
        try (FileOutputStream output = new FileOutputStream(journal);
             ObjectOutput outputObject = new ObjectOutputStream(output)) {
            for (Task task : tasks) {
                outputObject.writeObject(task);
            }
        }
    }

    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * delete by index
     *
     * @param index
     */
    public void delete(int index) {
        tasks.remove(index);
    }

    /**
     * delete by time
     *
     * @param time
     */
    public void delete(String time) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDateTime().equals(time)) tasks.remove(i);
        }
    }

    public void clean() {
        for (int i = 0; i < tasks.size(); i++) {
            tasks.remove(i);
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
