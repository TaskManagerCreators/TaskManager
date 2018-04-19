package taskmanagerlogic;

import com.repositories.HistoryRepository;
import com.taskmanagerlogic.History;
import com.taskmanagerlogic.InterAction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * It's a test for History. Before testing it runs the main application and works with the database.
 * @version 1.0
 * @see com.taskmanagerlogic.History
 */
@ComponentScan("com.repositories.HistoryRepository")
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("HistoryRepository-test")

class HistoryTest {
    HistoryRepository historyRepository;
    ApplicationContext context;
    @Test
    void addCleanedTask() {
        context = SpringApplication.run(InterAction.class);
        historyRepository = context.getBean(HistoryRepository.class);
        historyRepository.deleteAll();
        int count = historyRepository.findAll().size();
        historyRepository.insert(new History());
        assertEquals(historyRepository.findAll().size() , count + 1);
    }
}