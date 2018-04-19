package taskmanagerlogic;

import com.repositories.JournalRepository;
import com.taskmanagerlogic.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.reaction.Sleep;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This is a test class for the cleaner. There are no database connection. We use 2 private classes
 * for the test without database using arrays.
 * The following classes are inside this class.
 * @see TestJournal
 * @see TestHistory
 * @version 1.0
 */
class CleanerTest {
    private Journal journal = new TestJournal();
    private History history = new TestHistory();

    /**
     * The main test method. At first it creates an uncompleted task. Then it makes the task completed and
     * the cleaner must delete the journal.
     */
    @Test
    void testClean(){
        Cleaner cleaner = new Cleaner(journal, history);
        Task task = new Task();
        task.setStatus(Action.SCHEDULED);
        cleaner.getJournal().add(task);
        assertEquals(cleaner.getJournal().getTasks().size() , 1);
        cleaner.getJournal().getTasks().get(0).setStatus(Action.COMPLETED);
        cleaner.clean();
        try {
            Thread.sleep(5000 + 1500);
            System.out.println(cleaner.getJournal().getTasks().size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(cleaner.getJournal().getTasks().isEmpty());
    }

    /**
     * A private class extended journal.
     * @see com.taskmanagerlogic.Journal
     * @version 1.0
     */
    private class TestJournal extends Journal{
        List<Task> tasks;
        TestJournal(){
            tasks = new ArrayList();
        }
        @Override
        public Task getLast() {
            return tasks.get(tasks.size() - 1);
        }

        @Override
        public synchronized void add(Task task) {
            tasks.add(task);
        }

        @Override
        public synchronized void delete(String name) {
            for (Task task : tasks){
                if(task.getName().equals(name)){
                    tasks.remove(task);
                }
            }
        }

        @Override
        public synchronized void delete(UUID id) {
            for (Task task : tasks){
                if(task.getId().equals(id)){
                    tasks.remove(task);
                }
            }
        }

        @Override
        public List<Task> findByName(String name) {
            List<Task> result = new ArrayList<>();
            for(Task task : tasks){
                if(task.getName().equals(name)){
                    result.add(task);
                }
            }
            return result;
        }



        @Override
        public List<Task> findByPeriodOfTime(Date from, Date to, int page, int size) {
            return super.findByPeriodOfTime(from, to, page, size);
        }

        @Override
        public List<Task> findByPeriodOfTime(Date from, Date to) {
            return super.findByPeriodOfTime(from, to);
        }

        @Override
        public synchronized void delete(Action status) {
            super.delete(status);
        }

        @Override
        public synchronized void delete(Date from, Date to) {
            super.delete(from, to);
        }

        @Override
        public Task findById(UUID id) {
            for(Task task : tasks){
                if(task.getId().equals(id)){
                    return task;
                }
            }
            return null;
        }

        @Override
        public List<Task> findByStatus(Action status) {
            List<Task> result = new ArrayList<>();
            for(Task task : tasks){
                if(task.getStatus().equals(status)){
                    result.add(task);
                }
            }
            return result;
        }

        @Override
        public void clean() {
            tasks.clear();
        }

        @Override
        public List<Task> getTasks(int page, int size) {
            return super.getTasks(page, size);
        }

        @Override
        public List<ErrorRecord> getErrorRecords() {
            return super.getErrorRecords();
        }

        @Override
        public List<History> getHistory() {
            return super.getHistory();
        }

        @Override
        public List<Task> getTasks() {
            return tasks;
        }

        @Override
        public List<Task> findAllByEmail(int page, int size, String email) {
            return super.findAllByEmail(page, size, email);
        }

        @Override
        public List<Task> findByNameAndEmail(int page, int size, String part, String email) {
            return super.findByNameAndEmail(page, size, part, email);
        }

        @Override
        public void setContacts(Task task, List<String> contacts) {
            super.setContacts(task, contacts);
        }

        @Override
        public long length() {
            return tasks.size();
        }

        @Override
        public void updateStatus(UUID id, Action status) {
            super.updateStatus(id, status);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void schedule(Task task) {
            super.schedule(task);
        }

        @Override
        public void schedule() {
            super.schedule();
        }

        @Override
        public void scheduleNotification(Notification notification) {
            super.scheduleNotification(notification);
        }

        @Override
        public void reload() {
            super.reload();
        }
    }
    /**
     * A private class extended history.
     * @see com.taskmanagerlogic.History
     * @version 1.0
     */
    private class TestHistory extends History{
        private List<History> histories = new ArrayList<>();
        @Override
        public Date getEventDate() {
            return super.getEventDate();
        }

        @Override
        public Task getCurrentTask() {
            return super.getCurrentTask();
        }

        @Override
        public String getEvent() {
            return super.getEvent();
        }

        public TestHistory() {
            super();
        }

        public TestHistory(Task task, String operation, Date eventDate) {
            super(task, operation, eventDate);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void addCleanedTask(Task task) {
                if (histories.size() == 20) {
                    histories.clear();
                }
                this.setEventDate(new Date());
                this.setEvent("clean");
                this.setCurrentTask(task);
                histories.add(new History(this.getCurrentTask(), this.getEvent(), this.getEventDate()));

            }
        }
    }
