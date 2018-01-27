package commands;

import com.commands.Create;
import com.commands.Show;
import com.taskmanagerlogic.Journal;
import org.junit.jupiter.api.Test;
import com.taskmanagerlogic.Action;
import com.taskmanagerlogic.Journal;

import java.text.ParseException;
import java.util.zip.DataFormatException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

class ShowTest {
    private Create create;
    private Show show;

    @Test
    public void executeTest() {
        Journal journal = new Journal();
        //create = new Create(journal);
       // show = new Show(journal);
        assertTrue(journal.getTasks().isEmpty());
        try {
            create.execute("create test , test , 1:01 12.12.2018 , sleep - 100");
            assertFalse(journal.getTasks().isEmpty());
            assertTrue(journal.getTasks().get(0).getStatus() == Action.SCHEDULED);
            show.execute("-s scheduled");

        } catch (ParseException | DataFormatException e) {
            fail();
        }
    }

}