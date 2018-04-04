package com.repositories;

import com.taskmanagerlogic.History;
import com.taskmanagerlogic.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoryRepository extends MongoRepository<History, UUID> {
}
