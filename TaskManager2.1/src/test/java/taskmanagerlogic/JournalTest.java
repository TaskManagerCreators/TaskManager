package taskmanagerlogic;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import taskManagerCreators.reaction.Output;
import taskManagerCreators.reaction.Sleep;
import taskManagerCreators.taskmanagerlogic.Action;
import taskManagerCreators.taskmanagerlogic.Journal;
import taskManagerCreators.taskmanagerlogic.Task;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;


class JournalTest {

    private Journal journal;
    private List<Task> result;
    private Calendar calendar;
    private final static String JOURNAL_FILE_NAME = "journal.txt";
    private String name;
    private UUID id;
    private Date from;
    private Date to;
    private Date date;
    private Action status;

    @BeforeEach
    public void initTest() {
        journal = new Journal();
        calendar = Calendar.getInstance();
        date = Date.from(calendar.toInstant());
    }

    @After
    public void afterTest() {
        journal = null;
    }

    @Test
    public void testDeleteById() {
        Task task = new Task("test", "test", new Date(), new Output("Heyyy"), new ArrayList<>());
        id = task.getId();
        journal.add(task);
        assertFalse(journal.findById(id).isEmpty());
        journal.delete(id);
        assertTrue(journal.findById(id).isEmpty());
    }

    @Test
    public void testDeleteByName() {
        Task task = new Task("test", "test", new Date(), new Output("Heyyy"), new ArrayList<>());
        name = task.getName();
        journal.add(task);
        assertFalse(journal.findByName(name).isEmpty());
        journal.delete(name);
        assertTrue(journal.findByName(name).isEmpty());
    }


    @Test
    public void testDeleteByStatus() {
        Task task = new Task("test", "test", new Date(), new Output("Heyyy"), new ArrayList<>());
        status = task.getStatus();
        journal.add(task);
        assertFalse(journal.findByStatus(status).isEmpty());
        journal.delete(status);
        assertTrue(journal.findByStatus(status).isEmpty());
    }

    @Test
    public void testDeleteByPeriodOfTime() {
        result = journal.getTasks();
        from = Date.from(calendar.toInstant().minusMillis(100000));
        to = Date.from(calendar.toInstant().plusMillis(100000));
        result.removeAll(journal.findByPeriodOfTime(from, to));
        journal.delete(from, to);
        assertArrayEquals(result.toArray(), journal.getTasks().toArray());
    }

    @Test
    public void testFindByID() {
        result = journal.findById(id);
        result.forEach((task) -> {
            if (!task.getId().equals(id)) {
                fail("Bad search.");
            }
        });
    }

    @Test
    public void testFindByStatus() {
        result = journal.findByStatus(status);
        result.forEach((task) -> {
            if (task.getStatus() != status) {
                fail("Bad search.");
            }
        });
    }

    @Test
    public void testFindByName() {
        result = journal.findByName(name);
        result.forEach((task) -> {
            if (task.getName() != name) {
                fail("Bad search.");
            }
        });
    }

    @Test
    public void testFindByPeriod() {
        from = Date.from(calendar.toInstant().minusMillis(100000));
        to = Date.from(calendar.toInstant().plusMillis(100000));
        result = journal.findByPeriodOfTime(from, to);
        result.forEach((task) -> {
            if (task.getTargetTime().after(to) && task.getTargetTime().before(from)) {
                fail("Bad search.");
            }
        });
    }

    @Test
    public void testSaveTask() {
        File file = new File(JOURNAL_FILE_NAME);
        Task task = new Task("test", "test", new Date(), new Output("Heyyy")
                , new ArrayList<>());
        long fileSize = file.length();
        try {
            journal.load(file);
            journal.add(task);
            journal.save();
            assertEquals(true, fileSize < file.length());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCleanJournal() {
        Task task = new Task("test", "test", new Date(),
                new Output("Heyyy"), new ArrayList<>());
        journal.add(task);
        assertTrue(!journal.getTasks().isEmpty());
        journal.clean();
        assertTrue(journal.getTasks().isEmpty());
    }

    @Test
    public void testFileLoad() {
        File file = new File(JOURNAL_FILE_NAME);
        journal.clean();
        journal.load(file);
        if (file.length() > 5) {
            assertTrue(!journal.getTasks().isEmpty());
        }
    }

    @Test
    public void testTasksAdd() {
        Task task = new Task("test", "test", new Date(),
                new Output("Heyyy"), new ArrayList<>());
        journal.clean();
        journal.add(task);
        assertTrue(!journal.getTasks().isEmpty());
    }

    @Test
    public void testSchedule() {
        Task task1 = new Task();
        task1.setName("Task1");
        task1.setDescribe("Desc1");
        task1.setTargetTime(Date.from(new Date().toInstant().plusMillis(10000)));
        task1.setReaction(new Sleep(5000));

        Task task2 = new Task();
        task2.setName("Task2");
        task2.setDescribe("Desc2");
        task2.setTargetTime(Date.from(new Date().toInstant().plusMillis(15000)));
        task2.setReaction(new Sleep(5000));
        ArrayList<String> contacts = new ArrayList<>();
        contacts.add("me");

        task1.setContacts(contacts);
        task2.setContacts(contacts);

        journal.add(task1);
        journal.add(task2);
        journal.schedule();

        try {
            sleep(11000);
            assertEquals(Action.RUNNING, task1.getStatus());
            assertNotEquals(Action.RUNNING, task2.getStatus());
            sleep(6000);
            assertEquals(Action.RUNNING, task2.getStatus());
            assertEquals(Action.COMPLETED, task1.getStatus());
            sleep(6000);
            assertEquals(Action.COMPLETED, task2.getStatus()
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}