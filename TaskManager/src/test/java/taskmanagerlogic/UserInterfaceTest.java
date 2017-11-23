package taskmanagerlogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.DataFormatException;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static taskmanagerlogic.UserInterface.*;

class UserInterfaceTest {
/*
    @BeforeEach
    public void initTest() {
        journal = new Journal();
    }

    @Test
    public void testDateMatcher() {
        SimpleDateFormat dateFormat = UserInterface.dateFormat;
        try {
            assertEquals(true, UserInterface.dateMatcher(dateFormat.parse("1:01 1.1.2018")));
            assertEquals(false, UserInterface.dateMatcher(dateFormat.parse("1:01 1.1.2017")));
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
                UserInterface.create(command);

                command = "";
            }

        } catch (DataFormatException | ParseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDelete() {
        String name = "Darwin", command = "delete -n " + name;
        deleteOrShow(command, DELETE);
        List<Task> result = journal.getTasks();
        result.removeAll(journal.findByName(name));
        assertArrayEquals(result.toArray(), journal.getTasks().toArray());

    }


    @Test
    public void testExecuting() {
        Task task = new Task();
        task.setName("Test");
        task.setDescribe("Test");
        task.setDateTime(Date.from(new Date().toInstant().plusMillis(1000 * 10)));
        journal.add(task);
        currentTask = searchTask();
        callback();
        assertEquals(false, task.isExecuted());
        try {
            sleep(10000 + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(true, task.isExecuted());


    }

*/
}