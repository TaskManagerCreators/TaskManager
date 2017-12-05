package taskmanagerlogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    private taskmanagerlogic.UserInterface ui;

    @BeforeEach
    public void initTest() {

        ui = new taskmanagerlogic.UserInterface();
    }

    @Test
    public void testDateMatcher() {
        SimpleDateFormat dateFormat = ui.getDateFormat();
       /* try {
           // assertEquals(true, ui.dateMatcher(dateFormat.parse("1:01 1.1.2018")));
            //assertEquals(false, ui.dateMatcher(dateFormat.parse("1:01 1.1.2017")));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void testCreateTaskMethod() {
        /*String name = "task", describe = "describe", command = "", date;
        List<String> contacts = new ArrayList<>();
        try {
            for (int i = 0; i < 5; i++) {
                date = "1:01 1.1.2018";
                System.out.println(date);
                command += "[create " + name + i + ',' + describe + i + ',' + date;
                for (int j = 0; j < 3; j++) {
                    contacts.add("contact" + j);
                    command += ',' + contacts.get(j);
                }
                command += ']';
                //ui.create(command);

                command = "";
            }

        } catch (DataFormatException | ParseException e) {
            fail(e.getMessage());
        }*/
    }

    @Test
    public void testDelete() {
        String name = "Darwin", command = "delete -n " + name;
       // ui.deleteOrShow(command, DELETE);

    }


    @Test
    public void testExecuting() {
        taskmanagerlogic.Task task = new taskmanagerlogic.Task();
        task.setName("Test");
        task.setDescribe("Test");
        task.setTargetTime(Date.from(new Date().toInstant().plusMillis(1000 * 10)));
        ui.getJournal().add(task);
        ui.schedule(task);
        assertEquals(false, task.getStatus() == taskmanagerlogic.Action.RUNNING);
        try {
            sleep(10000 + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(true, task.getStatus() == taskmanagerlogic.Action.COMPLETED);

    }


}