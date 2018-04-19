package com.controllers;

import com.InterAction;
import com.model.User;
import com.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
    ApplicationContext context;
    UserRepository repository;
    User user;
    @BeforeAll
    void setUp(){
        context = SpringApplication.run(InterAction.class);
        repository = context.getBean(UserRepository.class);
        user = new User();
        user.setName("test-name");
        user.setEmail("test-email");
        user.setPassword("test-password");
        if(repository.getByNameStartingWith(user.getName()) != null){
            repository.deleteUsersByName(user.getName());
        }
    }
    @Test
    void addUser() {
        int count = repository.findAll().size();
        repository.insert(user);
        assertEquals(count + 1 , repository.findAll().size());
        repository.delete(user);
    }

    @Test
    void getUser() {
        repository.insert(user);
        assertEquals(repository.getByNameStartingWith(user.getName()).get(0).getEmail() , user.getEmail());
        repository.delete(user);
    }

    @Test
    void getAllUsers() {
        List<User> users = repository.findAll();
        repository.insert(user);
        assertEquals(users.size() , repository.findAll().size() - 1);
        repository.delete(user);
    }

    @Test
    void getUsersByName() {
        repository.insert(user);
        assertEquals(repository.getByNameStartingWith(user.getName()).get(0).getName() , user.getName());
        repository.delete(user);

    }

    @Test
    void getUsersByDescription() {
        repository.insert(user);
        assertEquals(repository.getUsersByDescription(user.getDescription()).get(0).getEmail() , user.getEmail());
        repository.delete(user);
    }

    @Test
    void deleteUserByEmail() {
        repository.insert(user);
        int count = repository.findAll().size();
        repository.delete(user.getEmail());
        assertEquals(count - 1 , repository.findAll().size());
        for (User user : repository.findAll()){
            assertNotEquals(user.getEmail() , this.user.getEmail());
        }
    }

    @Test
    void deleteByName() {
        repository.insert(user);
        int count = repository.findAll().size();
        repository.deleteUsersByName(user.getName());
        assertEquals(count - 1 , repository.findAll().size());
        for (User user : repository.findAll()){
            assertNotEquals(user.getEmail() , this.user.getEmail());
        }
    }
}