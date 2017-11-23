package taskmanagerlogic;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JournalTest {
    private Journal journal;
    private List<Task> result;
    private Calendar calendar;
    private final static String JOURNAL_FILE_NAME = "journal.txt";
    private static String name;
    private static UUID id;
    private static Date from;
    private static Date to;
    private static Date date;
    private static boolean status;

/*
    @BeforeEach
    public void initTest() {
        journal = new Journal();
        Task task;
        calendar = Calendar.getInstance();
        date = Date.from(calendar.toInstant());
        for (int i = 0; i < 5; i++) {
            task = new Task("task" + i, "purpose" + i, date,
                    new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"})));

            if (i == new Random().nextInt() * 5) {
                name = task.getName();
                id = task.getId();
                date = task.getDateTime();
                status = task.isExecuted();
            }
            journal.add(task);
        }
    }

    @After
    public void afterTest() {
        journal = null;
    }

    @Test
    public void testDeleteById() {
        journal = new Journal();
        Task task = new Task("1" , "2" , new Date() , new ArrayList<>());
        id = task.getId();
        journal.add(task);
        assertFalse(journal.findById(id).isEmpty());
        journal.delete(id);
        assertTrue(journal.findById(id).isEmpty());
    }

    @Test
    public void testDeleteByName() {
        result = journal.getTasks();
        result.removeAll(journal.findByName(name));
        journal.delete(name);
        assertArrayEquals(result.toArray(), journal.getTasks().toArray());
    }


    @Test
    public void testDeleteByStatus() {
        result = journal.getTasks();
        result.removeAll(journal.findByStatus(status));
        journal.delete(status);
        assertArrayEquals(result.toArray(), journal.getTasks().toArray());
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
            if (task.isExecuted() != status) {
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
            if (task.getDateTime().after(to) && task.getDateTime().before(from)) {
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
            assertEquals(true, (fileSize < file.length()));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCleanJournal() {
        List<Task> tasks = journal.getTasks();
        tasks.removeAll(tasks);
        journal.clean();
        assertEquals(tasks.isEmpty(), journal.getTasks().isEmpty());
    }

    @Test
    public void testFileLoad() {
        File file = new File(JOURNAL_FILE_NAME);
        journal.load(file);
        assertNotEquals(null, journal.getTasks());
    }

    @Test
    public void testTasksAdd() {
        Task task = new Task("task", "purpose", new Date(),
                new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"})));
        List<Task> preAdd = journal.getTasks();
        preAdd.add(task);
        journal.getTasks().add(task);
        assertArrayEquals(preAdd.toArray(), journal.getTasks().toArray());
    }
*/
}