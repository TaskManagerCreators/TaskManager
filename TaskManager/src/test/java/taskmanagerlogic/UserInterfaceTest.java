package taskmanagerlogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reaction.Sleep;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    private Journal journal;

    @BeforeEach
    public void initTest() {

        journal = new Journal();
    }

    @Test
    public void testSchedule(){
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
            assertEquals(Action.RUNNING , task1.getStatus());
            assertNotEquals(Action.RUNNING , task2.getStatus());
            sleep(6000);
            assertEquals(Action.RUNNING , task2.getStatus());
            assertEquals(Action.COMPLETED , task1.getStatus());
            sleep(6000);
            assertEquals(Action.COMPLETED , task2.getStatus()
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}