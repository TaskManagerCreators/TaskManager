package taskManagerCreators.taskmanagerlogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import taskManagerCreators.reaction.Output;
import taskManagerCreators.taskmanagerlogic.Action;
import taskManagerCreators.taskmanagerlogic.Task;

import java.io.*;
import java.util.*;

/**
 * Class Journal describes journal which contains tasks and works with journal.txt file
 *
 * @version 1.0
 */
@Component("journal")
public class Journal {

    static {
        mongoOperations = (MongoOperations) InterAction.context.getBean("mongoTemplate");
    }

    private static MongoOperations mongoOperations;

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
        //tasks = mongoOperations.findAll(Task.class);
        tasks = new ArrayList<>();
        tasks.add(new Task("test" , "test" , Date.from(Calendar.getInstance().toInstant()), new Output("test"), new ArrayList<String>()));
    }


    public void save() throws IOException {
        mongoOperations.findAllAndRemove(Query.query(Criteria.where("name").exists(true)), Task.class);
        for (Task task : tasks)
            mongoOperations.save(task);
    }

    public Journal() {
        scheduler.setPoolSize(100);
        scheduler.initialize();
        load(new File("journal.txt"));
    }

    synchronized public void add(Task task) {
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
    synchronized public void delete(UUID id) {
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
    synchronized public void delete(String name) {
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
    synchronized public void delete(Action status) {
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
    synchronized public void delete(Date from, Date to) {
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
            if (tasks.get(i).getId() != null && tasks.get(i).getId().equals(id))
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

    /**
     * Schedules a task.
     *
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
