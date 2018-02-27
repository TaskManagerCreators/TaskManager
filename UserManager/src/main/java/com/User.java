package com;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * This class describes a user.
 * @version 1.0
 */
@Component
@ComponentScan(basePackages = "com")
@Document(collection = "users")
public class User {
    private String name;
    @Id
    private String email;
    private String description;
    public User(String name , String email , String description){
        this.name = name;
        this.email = email;
        this.description = description;
    }
    public User(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
