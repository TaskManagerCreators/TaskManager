package taskmanagerlogic;

import com.InterAction;
import com.mailservice.EmailService;
import com.mailservice.EmailServiceImpl;
import com.taskmanagerlogic.Action;
import com.taskmanagerlogic.Notification;
import com.taskmanagerlogic.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import javax.mail.AuthenticationFailedException;
import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationTest {
    Notification notification;
    ApplicationContext context;
    @BeforeAll
    void setUp(){
        context = SpringApplication.run(InterAction.class);
        notification = context.getBean(Notification.class);
    }
    @Test
    void run() {
        EmailService service = context.getBean(EmailServiceImpl.class);
        notification.setEmailService(service);
        Task task = new Task();
        task.setName("name");
        task.setEmail("email");
        task.setStatus(Action.SCHEDULED);
        task.setNotificationInterval(new long [120]);
        notification.setPresentlyTask(task);
        assertThrows(AuthenticationFailedException.class ,()->notification.run());
    }
}