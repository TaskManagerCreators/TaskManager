package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.taskmanagerlogic.*;

import java.text.ParseException;
import java.time.Instant;
import java.util.*;

import static com.commands.Command.dateFormat;

/**
 * Controller for rest api.
 * There are all requests which you can use from web.
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskResponse response;

    @Autowired
    private Journal journal;

    @Autowired
    private History history;

    @Autowired
    private Notification notification;

    /**
     * GET method for represent by name
     *
     * @param name
     */
    @RequestMapping(method = RequestMethod.GET, params = "name")
    public @ResponseBody
    List<Task> getTasksByName(@RequestParam("name") String name) {
        return journal.findByName(name);
    }


    @RequestMapping(method = RequestMethod.GET, params = {"name", "page", "size"})
    public @ResponseBody
    TaskResponse getByName(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<Task> tasks = journal.findByName(name, page, size);
        System.out.println(tasks.size());
        response.setSize(tasks.size());
        response.setTasks(tasks);
        return response;
    }

    /**
     * GET method for represent by status
     *
     * @param status
     */
    @RequestMapping(method = RequestMethod.GET, params = "status")
    public @ResponseBody
    List<Task> getTasksByStatus(@RequestParam("status") String status) {
        return journal.findByStatus(Action.valueOf(status.toUpperCase()));
    }


    @RequestMapping(method = RequestMethod.GET, params = {"page", "size"})
    public @ResponseBody
    TaskResponse getAllTasks(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<Task> tasks = journal.getTasks(page, size);
        response.setTasks(tasks);
        response.setSize(tasks.size());
        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<Task> getTasks() {
        return journal.getTasks();
    }


    /**
     * GET method for represent by period of time
     *
     * @param from
     * @param to
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.GET, params = {"from", "to"})
    public @ResponseBody
    List<Task> getTasksByPeriodOfTime(@RequestParam("from") String from, @RequestParam("to") String to) throws ParseException {
        Date fromDate = Date.from(Instant.ofEpochMilli(Long.valueOf(from)));
        Date toDate = Date.from(Instant.ofEpochMilli(Long.valueOf(to)));
        return journal.findByPeriodOfTime(fromDate, toDate);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"from", "to", "page", "size"})
    public @ResponseBody
    TaskResponse getByPeriodOfTime(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("page") int page, @RequestParam("size") int size) {
        Date fromDate = Date.from(Instant.ofEpochMilli(Long.valueOf(from)));
        Date toDate = Date.from(Instant.ofEpochMilli(Long.valueOf(to)));
        List<Task> tasks = journal.findByPeriodOfTime(fromDate, toDate, page, size);
        response.setTasks(tasks);
        response.setSize(tasks.size());
        return response;
    }

    /**
     * GET method for represent by id
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Task getTaskById(@PathVariable("id") String id) {
        return journal.findById(UUID.fromString(id));
    }


    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public @ResponseBody
    List<History> getHistory() {
        return journal.getHistory();
    }


    @RequestMapping(value = "/error_records", method = RequestMethod.GET)
    public @ResponseBody
    List<ErrorRecord> getErrorRecords() {
        System.out.println(journal.getErrorRecords());
        return journal.getErrorRecords();
    }


    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public @ResponseBody
    void cleanJournal() {
        journal.clean();
    }

    /**
     * DELETE method for delete by name
     *
     * @param name
     */
    @RequestMapping(method = RequestMethod.DELETE, params = "name")
    public void deleteByName(@RequestParam(value = "name") String name) {
        journal.delete(name);
    }

    /**
     * DELETE method for delete by id
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("id") String id) {
        journal.delete(UUID.fromString(id));
    }

    /**
     * DELETE method for delete by status
     *
     * @param status
     */
    @RequestMapping(method = RequestMethod.DELETE, params = "status")
    public void deleteByStatus(@RequestParam(value = "status") String status) {
        journal.delete(Action.valueOf(status.toUpperCase()));
    }

    /**
     * DELETE method for delete by period of time
     *
     * @param from
     * @param to
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.DELETE, params = {"from", "to"})
    public void deleteByPeriodOfTime(@RequestParam(value = "from") String from,
                                     @RequestParam(value = "to") String to) throws ParseException {
        Date fromDate = dateFormat.parse(from);
        Date toDate = dateFormat.parse(to);
        journal.delete(fromDate, toDate);
    }


    /**
     * POST method for create the Task
     *
     * @return Task
     * @throws ParseException
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public @ResponseBody
    Task addTasks(@RequestBody Task task) throws ParseException {
        journal.add(task);
        journal.schedule(task);
        notification.setPresentlyTask(task);
        journal.scheduleNotification(notification);
        return task;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Task putContacts(@PathVariable("id") String id, @RequestBody String contacts) {
        String[] communications = contacts.split(",");
        Task task = journal.findById(UUID.fromString(id));
        journal.setContacts(task, Arrays.asList(communications));
        return task;
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Check arguments!")
    @ExceptionHandler({IllegalArgumentException.class, ParseException.class,
            NumberFormatException.class, NullPointerException.class})
    public void handleExceptions() {
        System.out.println("ExceptionHandler executed");
    }
}
