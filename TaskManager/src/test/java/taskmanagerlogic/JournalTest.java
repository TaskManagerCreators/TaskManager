package taskmanagerlogic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JournalTest {

    private Journal journal = new Journal();
    public JournalTest(){}
    @Test
    public void testAdd() {
        Date date = new Date();
        String dateString = "11:01 01.0.2018";
        Task task = new Task("task" , "purpose" , new Date() , new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"})));
        System.out.println(task);
    }

}