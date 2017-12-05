package taskmanagerlogic;

import config.TaskManagerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reaction.Sleep;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CleanerTest {

    private Journal journal;
    private long millis;
    private Cleaner cleaner;
    private AnnotationConfigApplicationContext context;

    @BeforeEach
    public void initTest() {
        millis = new Random().nextInt(6 * 1000);
        context = new AnnotationConfigApplicationContext();
        context.register(TaskManagerConfig.class);
        context.refresh();
        SpringApplication.run(Cleaner.class);
        cleaner = (Cleaner) context.getBean("cleaner");
        journal = cleaner.getJournal();
    }

    @Test()
    public void testClean() {
        int size = journal.getTasks().size();
        Date date = (Date.from(new Date().toInstant().plusMillis(millis)));
        Task task = new taskmanagerlogic.Task("Test", "Test",
                date , new Sleep(millis + 1000), new ArrayList<>());
       journal.add(task);
        journal.schedule(journal.getLast());
        assertFalse(journal.getTasks().size() == size);
        try {
            Thread.sleep(millis * 2 + 5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(journal.getTasks().size() == size);
    }

}