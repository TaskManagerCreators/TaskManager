package config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import taskmanagerlogic.*;

/**
 * Contains beans configuration for CDI
 *
 * @version 1.0
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"taskmanagerlogic", "commands", "controller"})
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

}
