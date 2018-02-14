package com.controllers;

import com.User;
import com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This is controller for users. There are methods for CRUD operations here.
 * @version 1.0
 */
@Component
public class UserController {
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * This adds an user into the database.
     * @param user
     */
    synchronized public void addUser(User user){
        userRepository.insert(user);
    }

    /**
     * This gets an user from the database by the email.
     * @param email
     * @return user
     * @see User
     */
    public User getUser(String email){
        return userRepository.findOne(email);
    }

    /**
     * This gets all users
     * @return all users.
     */
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * This gets users by the name
     * @param name
     * @return users by the name
     */
    public List<User> getUsersByName(String name){
        return userRepository.getUsersByName(name);
    }

    /**
     * This gets users by the description
     * @param description
     * @return users by description
     */
    public List<User> getUsersByDiscription(String description){
        return userRepository.getUsersByDescription(description);
    }
}
