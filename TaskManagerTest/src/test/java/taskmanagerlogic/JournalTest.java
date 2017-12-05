package taskmanagerlogic;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;



class JournalTest {

    @Autowired
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

   // @BeforeEach
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
        Task task = new Task("test", "test", new Date(), new ArrayList<>());
        id = task.getId();
        journal.add(task);
        assertFalse(journal.findById(id).isEmpty());
        journal.delete(id);
        assertTrue(journal.findById(id).isEmpty());
    }

    @Test
    public void testDeleteByName() {
        Task task = new Task("test", "test", new Date(), new ArrayList<>());
        name = task.getName();
        journal.add(task);
        assertFalse(journal.findByName(name).isEmpty());
        journal.delete(name);
        assertTrue(journal.findByName(name).isEmpty());
    }


    @Test
    public void testDeleteByStatus() {
        Task task = new Task("test", "test", new Date(), new ArrayList<>());
        status = task.getStatus();
        journal.add(task);
        assertFalse(journal.findByStatus(status).isEmpty());
        journal.delete(status);
        assertTrue(journal.findByStatus(status).isEmpty());
        //System.out.println(taskmanagerlogic.Action.RUNNING.equals(taskmanagerlogic.Action.RUNNING));
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
        //result = new ArrayList<>(Arrays.asList(new taskmanagerlogic.Task("1", "2", new Date(), null)));
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
        Task task = new Task("task", "purpose", new Date(),
                new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"})));
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
        Task task = new Task("task", "purpose", new Date(),
                new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"})));
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
        Task task = new Task("task", "purpose", new Date(),
                new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"})));
        journal.clean();
        journal.add(task);
        assertTrue(!journal.getTasks().isEmpty());
    }


    @Test
    public void test(){
       // UserInterface ui = new UserInterface();
        //System.out.println(ui);
        //System.out.println(ui.getJournal());
        //System.out.println(ui.getJournal().getTasks());
        UserInterface ui = new UserInterface();

    }

}