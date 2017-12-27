package taskmanagerlogic;

import org.junit.jupiter.api.Test;
import taskManagerCreators.taskmanagerlogic.History;
import taskManagerCreators.taskmanagerlogic.Task;

import static org.junit.jupiter.api.Assertions.*;


class HistoryTest {

    @Test
    void addCleanedTask() {
        History history = new History();
        assertTrue(history.getCleanedTasks().isEmpty());
        history.addCleanedTask(new Task());
        assertFalse(history.getCleanedTasks().isEmpty());
    }

}