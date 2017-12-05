package config;

import commands.Create;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import taskmanagerlogic.*;

import java.util.ArrayList;
import java.util.List;


//@Configuration
//@ComponentScan(basePackages = "taskmanagerlogic")

@Configuration
@ComponentScan(basePackages = "taskmanagerlogic")
public class TaskManagerConfig {

    @Bean
    public Cleaner cleaner() {
        return new Cleaner(userInterface());
    }

    @Bean
    public UserInterface userInterface() {
        return new UserInterface();
    }

    @Bean
    public InterAction interAction(UserInterface ui){
        InterAction interAction = new InterAction(ui);
        //interAction.setCleaner(cleaner());
        //interAction.setUi(userInterface());
        return interAction;
    }

    @Bean
    public Journal journal() {
        Journal journal = new Journal();
       // journal.setTasks(tasks);
        return journal;
    }


    @Bean
    public Task task() {
        return new Task();
    }

    /*public Create create(){
        return new Create();
    }*/

}
