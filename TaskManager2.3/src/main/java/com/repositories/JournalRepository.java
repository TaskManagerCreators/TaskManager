package com.repositories;

import com.taskmanagerlogic.Action;
import com.taskmanagerlogic.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface JournalRepository extends MongoRepository<Task, UUID> {

    void deleteByName(String name);

    void deleteByStatus(Action status);

    List<Task> findByName(String name);

    List<Task> findByStatus(Action status);

    List<Task> findByNameStartingWith(String part);

    List<Task> findByTargetTimeBetween(Date from, Date to);

    void deleteByTargetTimeBetween(Date from , Date to);

    Page findByTargetTimeBetween(Pageable pageable , Date from, Date to);

    Page findByNameStartingWith(Pageable pageable , String part);
    void deleteByEmail(String email);
}
