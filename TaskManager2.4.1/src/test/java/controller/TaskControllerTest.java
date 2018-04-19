package controller;


import com.InterAction;
import com.controller.TaskController;
import com.reaction.Sleep;
import com.taskmanagerlogic.History;
import com.taskmanagerlogic.Journal;
import com.taskmanagerlogic.Task;
import com.taskmanagerlogic.TaskResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskControllerTest {
    @Test
    public void check(){
        assertEquals(2,2);
    }
    private MockMvc mockMvc;
    private TaskController controller;
    private ApplicationContext context;
    Journal journal;
    MvcResult result;
    Task task;
    @BeforeAll
    void setUp(){
        context = SpringApplication.run(InterAction.class);
        controller = context.getBean(TaskController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        journal = context.getBean(Journal.class);
       task = new Task(
                "Test",
                "Test",
                new Date(),
                new Sleep(1000),
                new ArrayList<>()
        );
       if(journal.findByName(task.getName()) != null){
           journal.getJournalRepository().deleteByName(task.getName());
       }
    }
    @Test
    void getTasksByName() throws Exception {
        journal.add(task);
       result = mockMvc.perform(MockMvcRequestBuilders.get("/tasks?name=" + task.getName())).andReturn();
        assertEquals(result.getResponse().getStatus() , 200);
        journal.getJournalRepository().deleteByName(task.getName());
    }

    @Test
    void getTasksByStatus() throws Exception {
        journal.add(task);
        result = mockMvc.perform(MockMvcRequestBuilders.get("/tasks?status=" + task.getStatus())).andReturn();
        assertEquals(result.getResponse().getStatus() , 200);
        journal.getJournalRepository().deleteByName(task.getName());

    }


    @Test
    void getTasksById() throws Exception {

        journal.add(task);
        result = mockMvc.perform(MockMvcRequestBuilders.get("/tasks/"+task.getId())).andReturn();
        assertEquals(result.getResponse().getStatus() , 200);
        journal.getJournalRepository().deleteByName(task.getName());

    }

    @Test
    void getHistory() throws Exception {
        result = mockMvc.perform(MockMvcRequestBuilders.get("/tasks/history")).andReturn();
        assertEquals(result.getResponse().getStatus() , 200);


    }

    @Test
    void deleteByName() throws Exception {
        journal.add(task);
        int count = journal.getTasks().size();
        result = mockMvc.perform(MockMvcRequestBuilders.delete("/tasks?name=" + task.getName())).andReturn();
        assertEquals(count - 1 , journal.getTasks().size());
        journal.getJournalRepository().deleteByName(task.getName());
    }

    @Test
    void getTasks() {
        try {
            result = mockMvc.perform(MockMvcRequestBuilders.get("/tasks")).andReturn();
            assertEquals(result.getResponse().getStatus() , 200);
            System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTaskById() {
        journal.add(task);
        try {
            result = mockMvc.perform(MockMvcRequestBuilders.get("/tasks?id="+task.getId())).andReturn();
            assertEquals(result.getResponse().getStatus() , 200);
            journal.getJournalRepository().delete(task.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteById() {
        journal.add(task);
        int count = journal.getTasks().size();
        try {
            result = mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/" + task.getId())).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(count - 1 , journal.getTasks().size());
        journal.getJournalRepository().deleteByName(task.getName());

    }
}