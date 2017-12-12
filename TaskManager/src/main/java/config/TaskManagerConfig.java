package config;

import commands.Command;
import commands.Create;
import commands.Delete;
import commands.Show;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import taskmanagerlogic.Cleaner;
import taskmanagerlogic.InterAction;
import taskmanagerlogic.Journal;
import taskmanagerlogic.Task;

/**
 * Contains beans configuration for CDI
 */
@Configuration
@ComponentScan(basePackages = {"taskmanagerlogic", "commands"})
public class TaskManagerConfig {

    @Bean
    public Cleaner cleaner() {
        return new Cleaner(journal());
    }


    @Bean
    public InterAction interAction() {
        return new InterAction(cleaner(), journal());
    }

    @Bean
    public Journal journal() {
        return new Journal();
    }

    @Bean
    public Task task() {
        return new Task();
    }

    @Qualifier("create")
    @Bean
    public Command create() {
        return new Create(journal());
    }

    @Qualifier("delete")
    @Bean
    public Command delete() {
        return new Delete(journal());
    }

    @Qualifier("show")
    @Bean
    public Command show() {
        return new Show(journal());
    }

}
