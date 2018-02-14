package com.repositories;

import com.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface is for the mongo repository.
 * @version 1.0
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> getUsersByName(String name);
    List<User> getUsersByDescription(String description);
}
