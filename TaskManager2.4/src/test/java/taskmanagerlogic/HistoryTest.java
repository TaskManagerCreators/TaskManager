package taskmanagerlogic;

import com.taskmanagerlogic.History;
import com.taskmanagerlogic.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class HistoryTest {

    @Test
    void addCleanedTask() {
        History history = new History();
        //assertTrue(history.getCleanedTasks().isEmpty());
        history.addCleanedTask(new Task());
        //assertFalse(history.getCleanedTasks().isEmpty());
    }

}