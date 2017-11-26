package taskmanagerlogic;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Class Journal describes journal which contains tasks and works with journal.txt file
 *
 * @version 1.0
 */

public class Journal {

    private List<Task> tasks = new ArrayList<>();
    /**
     * History for deleted tasks
     */
    private History history = new History();
    /**
     * Contains serializable tasks
     */
    private File journal;

    /**
     * Create once new object and create journal file if it does not exist
     * Fills  collection of tasks if file is empty
     */
    public void load(File file) {
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
        history.load();
    }


    public void save() throws IOException {
        try (FileOutputStream output = new FileOutputStream(journal);
             ObjectOutput outputObject = new ObjectOutputStream(output)) {
            for (Task task : tasks) {
                outputObject.writeObject(task);
            }
            history.save();
        }
    }

    public Journal() {

    }

    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public String toString() {
        String journal = "Tasks ------>\n\n";
        for (Task task : tasks) {
            journal += task;
            journal += '\n';
        }
        return journal + "<-------";
    }


    /**
     * delete by ID
     *
     * @param id
     */
    public void delete(UUID id) {
        tasks.removeAll(findById(id));
    }

    /**
     * delete by index
     *
     * @param name
     */
    public void delete(String name) {
        tasks.removeAll(findByName(name));
    }

    /**
     * delete by status
     *
     * @param status
     */

    public void delete(Action status) {
        tasks.removeAll(findByStatus(status));
    }

    /**
     * delete by period of time
     *
     * @param from
     * @param to
     */
    public void delete(Date from, Date to) {
        tasks.removeAll(findByPeriodOfTime(from, to));
    }

    public List<Task> findByName(String name) {
        List<Task> particularTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getName().equals(name))
                particularTasks.add(tasks.get(i));
        }
        return particularTasks;
    }

    public List<Task> findByPeriodOfTime(Date from, Date to) {
        List<Task> particularTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTargetTime().after(from) && tasks.get(i).getTargetTime().before(to))
                particularTasks.add(tasks.get(i));
        }
        return particularTasks;
    }

    public List<Task> findById(UUID id) {
        List<Task> particularTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(id))
                particularTasks.add(tasks.get(i));
        }
        return particularTasks;
    }

    public List<Task> findByStatus(Action status) {
        List<Task> particularTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getStatus() == status)
                particularTasks.add(tasks.get(i));
        }
        return particularTasks;
    }

    public void clean() {
        tasks.clear();
    }

    public List<Task> getTasks() {
        return tasks;
    }


    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
}
