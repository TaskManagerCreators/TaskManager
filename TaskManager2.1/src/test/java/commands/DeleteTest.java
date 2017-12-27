package commands;

import org.junit.jupiter.api.Test;
import taskManagerCreators.commands.Create;
import taskManagerCreators.commands.Delete;
import taskManagerCreators.taskmanagerlogic.Action;
import taskManagerCreators.taskmanagerlogic.Journal;

import java.text.ParseException;
import java.util.zip.DataFormatException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

class DeleteTest {

    private Delete delete;
    private Create create;

    @Test
    public void executeTest() {
        Journal journal = new Journal();
        create = new Create(journal);
        delete = new Delete(journal);
        assertTrue(journal.getTasks().isEmpty());
        try {
            create.execute("create test , test , 1:01 12.12.2018 , sleep - 100");
            assertFalse(journal.getTasks().isEmpty());
            assertTrue(journal.getTasks().get(0).getStatus() == Action.SCHEDULED);
            delete.execute("-s scheduled");
            assertTrue(journal.getTasks().isEmpty());

        } catch (ParseException | DataFormatException e) {
            fail();
        }
    }


}