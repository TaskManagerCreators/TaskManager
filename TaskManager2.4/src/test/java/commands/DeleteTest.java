package commands;

import com.commands.Create;
import com.commands.Delete;
import com.taskmanagerlogic.Action;
import com.taskmanagerlogic.Journal;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.zip.DataFormatException;

import static junit.framework.Assert.*;

class DeleteTest {

    private Delete delete;
    private Create create;

    @Test
    public void executeTest() {
        Journal journal = new Journal();
        //create = new Create(journal);
        //delete = new Delete(journal);
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