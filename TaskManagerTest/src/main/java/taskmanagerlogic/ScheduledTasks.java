package taskmanagerlogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


@EnableScheduling
//@Service
public class ScheduledTasks {

    //@Autowired
    private TestBean bean;
    private int i = 0;

    /*@Autowired
    public void setBean(TestBean bean) {
        this.bean = bean;
    }*/


    @Scheduled(fixedRate = 5000)
    public void print() {
        System.out.println(i);
        i++;
       // System.out.println(bean.getMsg());
    }

}
