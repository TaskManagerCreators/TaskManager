package taskmanagerlogic;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;


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
        try {
            String out = task.toString();
            assertNotEquals(null, out);
        } catch (Exception e) {
            fail("Bad output.");
        }
    }

    public int factorial(int num) {
        return (num == 0) ? 1 : num * factorial(num - 1);
    }


}