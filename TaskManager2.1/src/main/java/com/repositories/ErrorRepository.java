package com.repositories;

import com.taskmanagerlogic.ErrorRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ErrorRepository extends MongoRepository<ErrorRecord, UUID> {
}
