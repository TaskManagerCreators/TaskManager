package com.repositories;

import com.taskmanagerlogic.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface HistoryRepository extends MongoRepository<Task, UUID> {
}
