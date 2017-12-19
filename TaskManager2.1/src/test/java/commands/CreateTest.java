package commands;

import org.junit.jupiter.api.Test;
import taskmanagerlogic.Journal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.zip.DataFormatException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

class CreateTest {

    private Create create;

    @Test
    public void executeTest(){
        Journal journal = new Journal();
        create = new Create(journal);
        assertTrue(journal.getTasks().isEmpty());
        try {
            create.execute("test , test , 1:01 12.12.2018 , sleep - 100");
        } catch (ParseException | DataFormatException e) {
           fail();
        }
        assertFalse(journal.getTasks().isEmpty());
    }

    @Test
    public void testDateMatcher() {
        create = new Create();
        SimpleDateFormat dateFormat = Command.dateFormat;
        try {
           assertEquals(true, create.dateMatcher(dateFormat.parse("1:01 1.1.2018")));
           assertEquals(false, create.dateMatcher(dateFormat.parse("1:01 1.1.2017")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}