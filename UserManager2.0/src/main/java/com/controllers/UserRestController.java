package com.controllers;

import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

/**
 * This is rest controller.
 * There are all requests which you can use in web.
 *
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
     *
     * @return all users
     */
    @PreAuthorize("hasAuthority('SUPER_USER')")
    @PostAuthorize("hasAuthority('SUPER_USER')")
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<User> getAllUsers() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getName());
        return userController.getAllUsers();
    }

    /**
     * Get method for receiving user by email.
     * email is the key for user.
     *
     * @param email
     * @return user by email.
     */
    @RequestMapping(method = RequestMethod.GET, params = "email")
    public @ResponseBody
    User getUserByEmail(@RequestParam("email") String email) {
        return userController.getUser(email);
    }

    /**
     * Get method for receiving users by name
     *
     * @param name
     * @return users by name
     */
    @RequestMapping(method = RequestMethod.GET, params = "name")
    public @ResponseBody
    List<User> getUsersByName(@RequestParam("name") String name) {
        return userController.getUsersByName(name);
    }

    /**
     * Get method for receiving users by description
     *
     * @param description
     * @return users by description
     */
    @RequestMapping(method = RequestMethod.GET, params = "desc")
    public @ResponseBody
    List<User> getUsersByDescription(@RequestParam("desc") String description) {
        return userController.getUsersByDescription(description);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    User addUser(@RequestBody User user) throws ParseException {
        userController.addUser(user);
        return user;
    }

    @RequestMapping(method = RequestMethod.DELETE, params = "email")
    public void deleteByEmail(@RequestParam(value = "email") String email) {
        //try {
            /*OkHttpClientHttpRequestFactory factory = new OkHttpClientHttpRequestFactory();
            ClientHttpRequest request = factory.createRequest(new URI("http://localhost:8080/tasks?email=" + email), HttpMethod.DELETE);
            ClientHttpResponse response = request.execute();*/
        //if (response.getStatusCode().is2xxSuccessful()) {
        userController.deleteUserByEmail(email);
        // }
        // } catch (URISyntaxException | IOException e) {
        //   e.printStackTrace();
        //}

    }

    @RequestMapping(method = RequestMethod.DELETE, params = "name")
    public void deleteByName(@RequestParam(value = "name") String name) {
        try {
            OkHttpClientHttpRequestFactory factory = new OkHttpClientHttpRequestFactory();
            ClientHttpRequest request = factory.createRequest(new URI("http://localhost:8080/tasks?name=" + name), HttpMethod.DELETE);
            ClientHttpResponse response = request.execute();
            if (response.getStatusCode().is2xxSuccessful()) {
                userController.deleteUserByEmail(name);
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
