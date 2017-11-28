package taskmanagerlogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class CleanerTest {

    private UserInterface ui;
    private long millis;
    private Cleaner cleaner;

    @BeforeEach
    public void initTest() {
        ui = new UserInterface();
        millis = new Random().nextInt(6 * 1000);
        cleaner = new Cleaner(ui);
    }

    @Test
    public void testClean() {

        Date date = (Date.from(new Date().toInstant().plusMillis(1000 * 10)));
        Task task = new Task("Test" , "Test" , date , new ArrayList<>());

        ui.getJournal().add(task);
        ui.setCurrentTask(ui.searchTask());
        ui.callback();
        assertFalse(ui.getJournal().getTasks().isEmpty());
        System.out.println(task.getId());
        cleaner.setDaemon(true);
        cleaner.start();

        try {
            sleep(10000 + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        assertTrue(ui.getJournal().getTasks().isEmpty());

    }

}