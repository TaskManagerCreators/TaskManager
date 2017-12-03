package taskmanagerlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class HistoryTest {

    @Test
    void addCleanedTask() {
        History history = new History();
        int size = history.getCleanedTasks().size();
        assertEquals(history.getCleanedTasks().size(), size);
        history.addCleanedTask(new Task());
        assertNotEquals(size, history.getCleanedTasks().size());
    }


    @Test
    void save() {
        History history = new History();
        long size = history.file.length();
        assertEquals(size, history.file.length());
        history.addCleanedTask(new Task());
        history.save();
        assertNotEquals(size, history.file.length());
    }

}