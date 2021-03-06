package taskmanagerlogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import taskmanagerlogic.Action;
import taskmanagerlogic.Task;

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
@Component("journal")
public class Journal {

    /**
     * History for deleted tasks
     */
    private History history = new History();

    /**
     * Contains serializable tasks
     */
    private File journal;

    private ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

    //@Autowired
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    private volatile List<Task> tasks = new ArrayList<>();

    public History getHistory() {
        return history;
    }

    @Autowired
    public void setHistory(History history) {
        this.history = history;
    }

    public Task getLast() {
        return tasks.get(tasks.size() - 1);
    }

    public ThreadPoolTaskScheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

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
    }


    public void save() throws IOException {
        try (FileOutputStream output = new FileOutputStream(journal);
             ObjectOutput outputObject = new ObjectOutputStream(output)) {
            for (Task task : tasks) {
                outputObject.writeObject(task);
            }
        }
    }

    public Journal() {
        scheduler.setPoolSize(100);
        scheduler.initialize();
        load(new File("journal.txt"));
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
     * Delete by ID
     *
     * @param id
     */
    public void delete(UUID id) {
        tasks.removeAll(findById(id));
        scheduler.shutdown();
        scheduler.initialize();
        Date date = new Date();
        for (Task t : tasks) {
            if (date.getTime() - t.getTargetTime().getTime() < 0)
                scheduler.schedule(t, t.getTargetTime());
        }
    }

    /**
     * Delete by index
     *
     * @param name
     */
    public void delete(String name) {
        tasks.removeAll(findByName(name));
        scheduler.shutdown();
        scheduler.initialize();
        Date date = new Date();
        for (Task t : tasks) {
            if (date.getTime() - t.getTargetTime().getTime() < 0)
                scheduler.schedule(t, t.getTargetTime());
        }
    }

    /**
     * Delete by status
     *
     * @param status
     */
    public void delete(Action status) {
        tasks.removeAll(findByStatus(status));
        scheduler.shutdown();
        scheduler.initialize();
        Date date = new Date();
        for (Task t : tasks) {
            if (date.getTime() - t.getTargetTime().getTime() < 0)
                scheduler.schedule(t, t.getTargetTime());
        }
    }

    /**
     * Delete by period of time
     *
     * @param from
     * @param to
     */
    public void delete(Date from, Date to) {
        tasks.removeAll(findByPeriodOfTime(from, to));
        scheduler.shutdown();
        scheduler.initialize();
        Date date = new Date();
        for (Task t : tasks) {
            if (date.getTime() - t.getTargetTime().getTime() < 0)
                scheduler.schedule(t, t.getTargetTime());
        }
    }

    public List<Task> findByName(String name) {
        List<Task> particularTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getName().equals(name))
                particularTasks.add(tasks.get(i));
        }
        return particularTasks;
    }

    public List<taskmanagerlogic.Task> findByPeriodOfTime(Date from, Date to) {
        List<taskmanagerlogic.Task> particularTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTargetTime().after(from) && tasks.get(i).getTargetTime().before(to))
                particularTasks.add(tasks.get(i));
        }
        return particularTasks;
    }

    public List<taskmanagerlogic.Task> findById(UUID id) {
        List<taskmanagerlogic.Task> particularTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() != null && tasks.get(i).getId().equals(id))
                particularTasks.add(tasks.get(i));
        }
        return particularTasks;
    }

    public List<taskmanagerlogic.Task> findByStatus(taskmanagerlogic.Action status) {
        List<taskmanagerlogic.Task> particularTasks = new ArrayList<>();
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

    /**
     * Schedules a task.
     * @param task
     */
    public void schedule(Task task) {
        Date date = new Date();
        if (date.getTime() - task.getTargetTime().getTime() < 0)
            scheduler.schedule(task, task.getTargetTime());
    }

    public void schedule() {
        Date date = new Date();
        for (Task t : tasks) {
            if (date.getTime() - t.getTargetTime().getTime() < 0) {
                scheduler.schedule(t, t.getTargetTime());
            }
        }
    }

}
