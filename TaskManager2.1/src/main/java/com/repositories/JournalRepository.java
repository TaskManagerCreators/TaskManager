package com.repositories;

import com.taskmanagerlogic.Action;
import com.taskmanagerlogic.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JournalRepository extends MongoRepository<Task, UUID> {

    void deleteByName(String name);

    void deleteByStatus(Action status);

    /*void deleteByPeriodOfTime(Date from, Date to);

    List<Task> findByPeriodOfTime(Date from, Date to);*/

    List<Task> findByName(String name);

    List<Task> findByStatus(Action status);

}
