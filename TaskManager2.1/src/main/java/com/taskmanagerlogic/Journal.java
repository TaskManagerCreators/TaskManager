package com.taskmanagerlogic;

import com.repositories.ErrorRepository;
import com.repositories.HistoryRepository;
import com.repositories.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Class Journal describes journal which contains tasks and works with journal.txt file
 *
 * @version 1.0
 */
@Component
public class Journal {

    private JournalRepository journalRepository;

    @Autowired
    public void setHistoryRepository(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    private HistoryRepository historyRepository;


    @Autowired
    public void setErrorRepository(ErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
    }

    private ErrorRepository errorRepository;

    @Autowired
    public void setJournalRepository(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    private ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private ThreadPoolTaskScheduler notificationScheduler = new ThreadPoolTaskScheduler();

    private Task laterAddedTask;

    public Task getLast() {
        return laterAddedTask;
    }


    public Journal() {
        scheduler.setPoolSize(100);
        notificationScheduler.setPoolSize(100);
        notificationScheduler.initialize();
        scheduler.initialize();
    }

    synchronized public void add(Task task) {
        journalRepository.insert(task);
        laterAddedTask = task;
    }

    synchronized public void delete(String name) {
        journalRepository.deleteByName(name);
        reload();
    }

    synchronized public void delete(UUID id) {
        journalRepository.delete(id);
        reload();
    }

    public List<Task> findByName(String name) {
        return journalRepository.findByNameStartingWith(name);
    }

    public List<Task> findByName(String name, int page, int size) {
        return journalRepository.findByNameStartingWith(new PageRequest(page - 1, size), name);
    }


    public List<Task> findByPeriodOfTime(Date from, Date to, int page, int size) {
        return journalRepository.findByTargetTimeBetween(new PageRequest(page - 1, size), from, to);
    }

    public List<Task> findByPeriodOfTime(Date from, Date to) {
        return journalRepository.findByTargetTimeBetween(from, to);
    }


    synchronized public void delete(Action status) {
        journalRepository.deleteByStatus(status);
        reload();
    }


    synchronized public void delete(Date from, Date to) {
        journalRepository.deleteByTargetTimeBetween(from, to);
        reload();
    }


    public Task findById(UUID id) {
        return journalRepository.findOne(id);
    }

    public List<Task> findByStatus(Action status) {
        return journalRepository.findByStatus(status);
    }

    public void clean() {
        journalRepository.deleteAll();
    }

    public List<Task> getTasks(int page, int size) {
        return journalRepository.findAll(new PageRequest(page - 1, size)).getContent();
    }

    public List<ErrorRecord> getErrorRecords() {
        return errorRepository.findAll();
    }

    public List<History> getHistory() {
        return historyRepository.findAll();
    }

    public List<Task> getTasks() {
        return journalRepository.findAll();
    }

    public void setContacts(Task task, List<String> contacts) {
       /* mongoTemplate.upsert(
                Query.query(Criteria.where("id").is(task.getId().toString())),
                Update.update("contacts", contacts), Task.class
        );*/
    }

    public long length() {
        return journalRepository.count();
    }


    public void updateStatus(UUID id, Action status) {
        Task task = findById(id);
        task.setStatus(status);
        journalRepository.save(task);
    }

    @Override
    public String toString() {
        String journal = "Tasks : \n{\n";
        for (Task task : getTasks()) {
            journal += task;
            journal += '\n';
        }
        return journal + "}";
    }

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

    public void scheduleNotification(Notification notification) {
        notificationScheduler.schedule(notification,
                Date.from(Instant.ofEpochMilli(notification.getPresentlyTask().getTargetTime().getTime()
                        - notification.getPresentlyTask().getNotificationInterval())));
    }

    public void reload() {
        scheduler.shutdown();
        scheduler.initialize();
        schedule();
    }

}
