import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import taskmanagerlogic.UserInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("A special TaskManager test")
public class TaskManagerTest {

    @BeforeAll
    @Test
    void test() {
        assertEquals(true, UserInterface.isDigit("0"));
        assertEquals(true, UserInterface.dateMatcher("1:11 am 1.01.2018"));
        assertEquals(false, UserInterface.dateMatcher("01:11 1.01.2018 "));
    }
}
