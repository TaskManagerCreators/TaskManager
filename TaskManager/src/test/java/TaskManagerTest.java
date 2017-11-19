import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import taskmanagerlogic.Journal;
import taskmanagerlogic.Task;
import taskmanagerlogic.UserInterface;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("A special TaskManager test")
public class TaskManagerTest {


    @Test
    void test() {
        /*String dateString = "11:01 01.0.2018";
        Task task = new Task("task" , "purpose" , new Date() , new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"})));
        //System.out.println(task);
        Journal journal = new Journal();
        journal.add(task);
        journal.add(task);
        System.out.println(journal);*/
        /*Journal journal = new Journal();
        List<Task> pre = new ArrayList<>();
        Date date  = new Date();
        for(int i = 0; i < 20 ; i ++) {
            Task task = new Task(String.valueOf(i), "purpose", date, new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"})));
            pre.add(task);
        }

        List<Task> list = new ArrayList<>();
        list.add(new Task(String.valueOf(12), "purpose", date, new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"}))));

        pre.forEach((t) -> System.out.println(t));

        list.forEach((t) -> System.out.println(t));

        pre.retainAll(list);
        System.out.println("After retainAll \n");
        pre.forEach((task -> System.out.println(task)));

        //journal.getTasks().re//
*/
    }
}
