package com.controllers;

import com.InterAction;
import com.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRestControllerTest {
    ApplicationContext context;
    MockMvc mockMvc;
    MvcResult result;
    User user;
    UserRestController controller;
    @BeforeAll
    void setUp(){
        context = SpringApplication.run(InterAction.class);
        controller = context.getBean(UserRestController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        user = new User();
        user.setName("test-Name");
        user.setEmail("test-Email");
        user.setPassword("test-Password");
        if(controller.getUserController().getUsersByName(user.getName()) != null){
            controller.getUserController().deleteByName(user.getName());
        }
    }

    @Test
    void getAllUsers() {
        try {
            result = mockMvc.perform(MockMvcRequestBuilders.get("/users")).andReturn();
            assertEquals(result.getResponse().getStatus() , 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUserByEmail() {
        controller.getUserController().getUserRepository().insert(user);
        try {
            result = mockMvc.perform(MockMvcRequestBuilders.get("/users?email=" + user.getEmail())).andReturn();
            assertEquals(result.getResponse().getStatus() , 200);
            controller.deleteByEmail(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUsersByName() {
        controller.getUserController().getUserRepository().insert(user);
        try {
            result = mockMvc.perform(MockMvcRequestBuilders.get("/users?name=" + user.getName())).andReturn();
            assertEquals(result.getResponse().getStatus() , 200);
            controller.deleteByEmail(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUsersByDescription() {
        controller.getUserController().getUserRepository().insert(user);
        try {
            result = mockMvc.perform(MockMvcRequestBuilders.get("/users?desc=" + user.getDescription())).andReturn();
            assertEquals(result.getResponse().getStatus() , 200);
            controller.deleteByEmail(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}