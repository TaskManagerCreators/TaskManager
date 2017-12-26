package taskmanagerlogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Class Journal describes journal which contains tasks and works with journal.txt file
 *
 * @version 1.0
 */
@Repository
@ComponentScan("taskmanagerlogic")
public class Journal {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * History for deleted tasks
     */
    private History history = new History();


    private ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();


    public History getHistory() {
        return history;
    }

    private Task laterAddedTask;

    @Autowired
    public void setHistory(History history) {
        this.history = history;
    }

    public Task getLast() {
        return laterAddedTask;
    }


    public Journal() {
        scheduler.setPoolSize(100);
        scheduler.initialize();
    }

    synchronized public void add(Task task) {
        mongoTemplate.insert(task);
        laterAddedTask = task;
    }

    synchronized public void delete(String name) {
        mongoTemplate.remove(Query.query(Criteria.where("name").in(name)));
        scheduler.shutdown();
        scheduler.initialize();
        Date date = new Date();
        for (Task t : getTasks()) {
            if (date.getTime() - t.getTargetTime().getTime() < 0)
                scheduler.schedule(t, t.getTargetTime());
        }
    }

    synchronized public void delete(UUID id) {
        mongoTemplate.remove(Query.query(Criteria.where("id").in(id.toString())));
        scheduler.shutdown();
        scheduler.initialize();
        Date date = new Date();
        for (Task t : getTasks()) {
            if (date.getTime() - t.getTargetTime().getTime() < 0)
                scheduler.schedule(t, t.getTargetTime());
        }
    }

    public List<Task> findByName(String name) {
        return mongoTemplate.find(Query.query(Criteria.where("name").in(name)), Task.class);
    }


    @Override
    public String toString() {
        String journal = "Tasks ------>\n\n";
        for (Task task : getTasks()) {
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


    /**
     * Delete by index
     *
     * @param name
     */


    /**
     * Delete by status
     *
     * @param status
     */
    synchronized public void delete(Action status) {
        mongoTemplate.remove(Query.query(Criteria.where("status").in(status.name())));
        scheduler.shutdown();
        scheduler.initialize();
        Date date = new Date();
        for (Task t : getTasks()) {
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
        mongoTemplate.remove(Query.query(Criteria.where("targetTime").gte(from).lte(to)), Task.class);
        scheduler.shutdown();
        scheduler.initialize();
        Date date = new Date();
        for (Task t : getTasks()) {
            if (date.getTime() - t.getTargetTime().getTime() < 0)
                scheduler.schedule(t, t.getTargetTime());
        }
    }


    public List<Task> findByPeriodOfTime(Date from, Date to) {
        return mongoTemplate.find(Query.query(Criteria.where("targetTime").gte(from).lte(to)), Task.class);
    }

    public Task findById(UUID id) {
        return mongoTemplate.findById(id, Task.class);
    }

    public List<Task> findByStatus(Action status) {
        return mongoTemplate.find(Query.query(Criteria.where("status").in(status.name())), Task.class);
    }

    public void clean() {
        mongoTemplate.remove(new Query(), "tasks");
    }

    public List<Task> getTasks() {
        return mongoTemplate.findAll(Task.class);
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
        for (Task t : getTasks()) {
            if (date.getTime() - t.getTargetTime().getTime() < 0) {
                scheduler.schedule(t, t.getTargetTime());
            }
        }
    }

    public long length() {
        return mongoTemplate.count(new Query(), "tasks");
    }

}
