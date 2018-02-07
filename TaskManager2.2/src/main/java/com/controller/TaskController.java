package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.taskmanagerlogic.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private Journal journal;

    @Autowired
    private History history;

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

    @RequestMapping(method = RequestMethod.GET , params = "next")
    public @ResponseBody
    List<Task> getAllTasks(@RequestParam("next") String next) {
        List<Task> result = journal.getTasks(next);
        result.get(0).setSize(journal.getTasks().size());
        return result;
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
        Date fromDate = dateFormat.parse(from);
        Date toDate = dateFormat.parse(to);
        return journal.findByPeriodOfTime(fromDate, toDate);
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
    String getHistory() {
        return history.toString();
    }


    @RequestMapping(value = "/error_records", method = RequestMethod.GET)
    public @ResponseBody
    List<ErrorRecord> getErrorRecords() {
        return null;//TODO:
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
    @RequestMapping(method = RequestMethod.DELETE, params = "id")
    public void deleteById(@RequestParam(value = "id") String id) {
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
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAll(){
        journal.clean();
    }


    /**
     * POST method for create the Task
     *
     * @return Task
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Task addTasks(@RequestBody Task task) throws ParseException {
        journal.add(task);
        journal.schedule(task);
        return task;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Task putContacts(@PathVariable("id") String id, @RequestBody String contacts) {
        System.out.println(contacts);
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