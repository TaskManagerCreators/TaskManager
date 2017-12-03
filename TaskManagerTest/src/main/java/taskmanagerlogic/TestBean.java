package taskmanagerlogic;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Component
public class TestBean {
    public String getMsg() {
        return "Hello";
    }
}
