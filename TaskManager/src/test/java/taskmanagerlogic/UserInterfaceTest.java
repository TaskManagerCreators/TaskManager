package taskmanagerlogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static taskmanagerlogic.UserInterface.*;

class UserInterfaceTest {

    private UserInterface ui;

    @BeforeEach
    public void initTest() {

        ui = new UserInterface();
    }

    @Test
    public void testDateMatcher() {
        SimpleDateFormat dateFormat = ui.getDateFormat();
        try {
            assertEquals(true, ui.dateMatcher(dateFormat.parse("1:01 1.1.2018")));
            assertEquals(false, ui.dateMatcher(dateFormat.parse("1:01 1.1.2017")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateTaskMethod() {
        String name = "task", describe = "describe", command = "", date;
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
                ui.create(command);

                command = "";
            }

        } catch (DataFormatException | ParseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDelete() {
        String name = "Darwin", command = "delete -n " + name;
        ui.deleteOrShow(command, DELETE);

    }

/*
    @Test
    public void testExecuting() {
        Task task = new Task();
        task.setName("Test");
        task.setDescribe("Test");
        task.setDateTime(Date.from(new Date().toInstant().plusMillis(1000 * 10)));
        ui.getJournal().add(task);
        ui.setCurrentTask( ui.searchTask());
        ui.callback();
        assertEquals(false, task.getStatus() == Action.RUNNING);
        try {
            sleep(10000 + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(true, task.isExecuted());


    }
*/

}