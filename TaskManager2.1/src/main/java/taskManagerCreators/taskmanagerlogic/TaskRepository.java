package taskManagerCreators.taskmanagerlogic;

//import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;


public interface TaskRepository extends MongoRepository<Task, UUID> {

    List<Task> findByName(String firstName);
}
