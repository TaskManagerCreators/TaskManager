package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import taskmanagerlogic.Action;
import taskmanagerlogic.InterAction;
import taskmanagerlogic.Journal;
import taskmanagerlogic.Task;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static commands.Command.dateFormat;

/**
 * Controller for rest api.
 * There are all requests which you can use from web.
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    public Journal journal;

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

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<Task> getAllTasks() {
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
        Date fromDate = dateFormat.parse(from);
        Date toDate = dateFormat.parse(to);
        return journal.findByPeriodOfTime(fromDate, toDate);
    }

    /**
     * GET method for represent by id
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = "id")
    public @ResponseBody
    Task getTaskById(@PathVariable("id") String id) {
        return journal.findById(UUID.fromString(id));
    }


    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public @ResponseBody
    String getHistory() {
        return journal.getHistory().toString();
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
        return task;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Task putContacts(@PathVariable("id") String id, @RequestBody String contacts) {
        String[] communications = contacts.split(",");
        Task task = journal.findById(UUID.fromString(id));
        task.setContacts(Arrays.asList(communications));
        return task;
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Check arguments!")
    @ExceptionHandler({IllegalArgumentException.class, ParseException.class,
            NumberFormatException.class, NullPointerException.class})
    public void handleExceptions() {
        System.out.println("ExceptionHandler executed");
    }
}
