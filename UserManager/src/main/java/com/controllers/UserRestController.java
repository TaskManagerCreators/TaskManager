package com.controllers;

import com.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * This is rest controller.
 * There are all requests which you can use in web.
 * @version 1.0
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserRestController {
    @Autowired
    UserController userController;

    /**
     * Get method for receiving all users.
     * @return all users
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<User> getAllUsers(){
        return userController.getAllUsers();
    }

    /**
     * Get method for receiving user by email.
     * email is the key for user.
     * @param email
     * @return user by email.
     */
    @RequestMapping(method = RequestMethod.GET , params = "email")
    public @ResponseBody
    User getUserByEmail(@RequestParam("email") String email){
        return userController.getUser(email);
    }

    /**
     * Get method for receiving users by name
     * @param name
     * @return users by name
     */
    @RequestMapping(method = RequestMethod.GET , params = "name")
    public @ResponseBody
    List<User> getUsersByName(@RequestParam("name") String name){
        return userController.getUsersByName(name);
    }

    /**
     * Get method for receiving users by description
     * @param description
     * @return users by description
     */
    @RequestMapping(method = RequestMethod.GET , params = "desc")
    public @ResponseBody
    List<User> getUsersByDescription(@RequestParam("desc") String description){
        return userController.getUsersByDiscription(description);
    }
    @RequestMapping(method = RequestMethod.POST , consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    User addUser(@RequestBody User user) throws ParseException{
        userController.addUser(user);
        return user;
    }
}
