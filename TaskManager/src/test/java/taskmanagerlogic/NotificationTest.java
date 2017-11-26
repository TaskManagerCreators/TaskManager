package taskmanagerlogic;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {
    @Test
    void run() {
        Notification notification = new Notification(new Task("test","test",new Date(),null));
        assertEquals(notification.getTask().getStatus(),Action.SCHEDULED);
        notification.run();
        assertEquals(notification.getTask().getStatus(),Action.COMPLETED);

    }

}