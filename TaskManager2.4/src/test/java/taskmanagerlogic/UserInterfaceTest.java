package taskmanagerlogic;

import com.taskmanagerlogic.Action;
import com.taskmanagerlogic.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    //private taskmanagerlogic.UserInterface ui;

    @BeforeEach
    public void initTest() {

        //ui = new UserInterface();
    }

    @Test
    public void testExecuting() {
        Task task = new Task();
        task.setName("Test");
        task.setDescribe("Test");
        task.setTargetTime(Date.from(new Date().toInstant().plusMillis(1000 * 10)));
        //ui.getJournal().add(task);
        assertEquals(false, task.getStatus() == Action.RUNNING);
        try {
            sleep(10000 + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(true, task.getStatus() == Action.COMPLETED);

    }


}