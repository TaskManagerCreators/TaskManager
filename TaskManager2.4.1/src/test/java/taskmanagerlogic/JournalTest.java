package taskmanagerlogic;

import com.InterAction;
import com.taskmanagerlogic.Journal;
import com.taskmanagerlogic.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The journal test. It's just a layer between the rest controller and the repositories.
 * Due that there are simple tests for checking. It uses the database.
 * @Warning Control deleting added tasks after using because there is used the relevant database. It's good to fix.
 * @see com.taskmanagerlogic.Journal
 * @version 1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JournalTest {
    private ApplicationContext context;
    private Journal journal;
    private Task testTask;
    private List<Task> tasks;
    @BeforeAll
    public void setUp() throws Exception {
        context = SpringApplication.run(InterAction.class);
        journal = context.getBean(Journal.class);
        testTask = new Task();
        testTask.setName("Test-Task"+new Date().toString());
        testTask.setEmail("Test-task-email@"+new Date().toString());
        journal.getJournalRepository().deleteByName(testTask.getName());
    }
    @Test
    void getLast() {
        journal.add(testTask);
        assertEquals(journal.getLast() , testTask);
        journal.getJournalRepository().delete(testTask.getId());
        System.out.println(journal.getLast().getId());
    }

    @Test
    void add() {
        journal.add(testTask);
        assertEquals(journal.getTasks().get(journal.getTasks().size() - 1).getId() , testTask.getId());
        journal.getJournalRepository().delete(testTask.getId());

    }

    @Test
    void delete() {
        int count = journal.getTasks().size();
        journal.add(testTask);
        journal.getJournalRepository().delete(testTask.getId());
        assertEquals(journal.getTasks().size() , count);
    }


    @Test
    void findByName() {
        journal.add(testTask);
        assertEquals(journal.findByName(testTask.getName()).get(0).getName() , testTask.getName());
        journal.getJournalRepository().delete(testTask.getId());

    }


    @Test
    void findById() {
        journal.add(testTask);
        assertEquals(journal.findById(testTask.getId()).getId() , testTask.getId());
        journal.getJournalRepository().delete(testTask.getId());

    }



    @Test
    void getTasks() {
        tasks = journal.getJournalRepository().findAll();
        assertEquals(tasks.size() , journal.getTasks().size());

    }

    @Test
    void getErrorRecords() {
        assertEquals(journal.getErrorRecords() , journal.getErrorRepository().findAll());
    }

    @Test
    void length() {
        assertEquals(journal.length() , journal.getJournalRepository().findAll().size());
    }

}