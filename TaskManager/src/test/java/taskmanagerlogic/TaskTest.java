package taskmanagerlogic;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static junit.framework.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class TaskTest {

    private Task task;


    @Before
    public void initTest() {

    }

    @After
    public void afterTest() {
        task = null;
    }

    @Test
    public void testOutput() {
        task = new Task("task", "purpose", new Date(),
                new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"})));

        String out = task.toString();
        assertNotEquals(null, out);
    }

}