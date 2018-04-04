package com.repositories;

import com.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface is for the mongo repository.
 */

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> getByNameStartingWith(String name);

    List<User> getUsersByDescription(String description);

    Optional<UserDetails> getByEmail(String email);

    void deleteUsersByName(String name);
}
