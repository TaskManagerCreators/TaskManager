package taskmanagerlogic;

import org.junit.jupiter.api.Test;
import reaction.Output;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {
    @Test
    void run() {
        Notification notification = new Notification(new Task("test",
                "test", new Date(), new Output("Heyyy") ,  new ArrayList<>()));
        assertEquals(notification.getTask().getStatus(), Action.SCHEDULED);
        notification.run();
        assertEquals(notification.getTask().getStatus(), Action.COMPLETED);
    }

}