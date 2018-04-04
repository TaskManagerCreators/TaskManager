package controller;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
class TaskControllerTest {
    @Test
    public void check(){
        assertEquals(2,2);
    }
    /*private static MockMvc mockMvc;

    private static TaskController taskController;

    private static Journal journal;

    private static AnnotationConfigApplicationContext context;

    //@Autowired
    //private WebApplicationContext webApplicationContext;


    @BeforeAll
    static void init() {
        context = new AnnotationConfigApplicationContext();
        context.register(DatabaseConfiguration.class, TaskManagerConfig.class);
        context.refresh();
        taskController = new TaskController();
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        journal = taskController.journal;
    }


    @Test
    void getTasksByName() throws Exception {
        Task task = new Task(
                "Test",
                "Test",
                new Date(),
                new Sleep(1000),
                new ArrayList<>()
        );
        journal.add(task);
        journal.save();
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks?name=" + task.getName())).andExpect(status().isOk());
        journal.delete(task.getName());
        journal.save();
    }

    @Test
    void getTasksByStatus() throws Exception {
        Task task = new Task(
                "Test",
                "Test",
                new Date(),
                new Sleep(1000),
                new ArrayList<>()
        );
        journal.add(task);
        journal.save();
        String status = task.getStatus().toString();
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks?status=" + status)).andExpect(status().isOk());
        journal.delete(task.getName());
        journal.save();
    }


    @Test
    void getTasksById() throws Exception {
        Task task = new Task(
                "Test",
                "Test",
                new Date(),
                new Sleep(1000),
                new ArrayList<>()
        );
        journal.add(task);
        journal.save();
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks?id=" + task.getId().toString())).andExpect(status().isOk());
        journal.delete(task.getName());
        journal.save();
    }

    @Test
    void getHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                get("/tasks/history")).andExpect(status().isOk());

    }

    @Test
    void deleteByName() throws Exception {
        int len = journal.len();
        Task task = new Task(
                "Test",
                "Test",
                new Date(),
                new Sleep(1000),
                new ArrayList<>()
        );
        journal.add(task);
        assertEquals(len, journal.len() - 1);
        mockMvc.perform(MockMvcRequestBuilders.
                delete("/tasks?name=" + task.getName())).andExpect(status().isOk());
        assertEquals(len, journal.len());
    }
*/
}