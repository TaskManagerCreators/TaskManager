package controller;

import commands.Create;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reaction.Reaction;
import taskmanagerlogic.*;

import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static commands.Command.dateFormat;
import static commands.Command.name;

/**
 * Controller for rest api.
 * There are all requests which you can use from web.
 * @version 1.0
 */
@CrossOrigin
@RestController
public class TaskController {

    private static Journal journal;

    static {
        journal = (Journal) InterAction.context.getBean("journal");
    }

    /**
     * GET method for represent by name
     *
     * @param name
     */

    @RequestMapping(value = "/tasks",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody
    List<Task> getTasksByName(@RequestParam(value = "name") String name) {
        return journal.findByName(name);
    }
    @RequestMapping(value = "tasks/all" , method = RequestMethod.GET)
    public @ResponseBody
    List<Task> getAllTasks(){
        return journal.getTasks();
    }

    /**
     * GET method for represent by status
     *
     * @param status
     */
    @RequestMapping(value = "/tasks/status/{status}", method = RequestMethod.GET)
    public @ResponseBody
    List<Task> getTasksByStatus(@PathVariable("status") String status) {
        return journal.findByStatus(Action.valueOf(status.toUpperCase()));
    }

    /**
     * GET method for represent by period of time
     *
     * @param from
     * @param to
     * @throws ParseException
     */
    @RequestMapping(value = "/tasks/date", method = RequestMethod.GET)
    public @ResponseBody
    List<Task> getTasksByPeriodOfTime(@RequestParam(value = "from") String from,
                                      @RequestParam(value = "to") String to) throws ParseException {
        Date fromDate = dateFormat.parse(from);
        Date toDate = dateFormat.parse(to);
        return journal.findByPeriodOfTime(fromDate, toDate);
    }

    /**
     * GET method for represent by id
     *
     * @param id
     */
    @RequestMapping(value = "/tasks/id/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<Task> getTasksById(@PathVariable("id") String id) {
        return journal.findById(UUID.fromString(id));
    }


    @RequestMapping(value = "/tasks/history", method = RequestMethod.GET)
    public @ResponseBody
    String getHistory() {
        return journal.getHistory().toString();
    }


    /**
     * DELETE method for delete by name
     *
     * @param name
     */
    @RequestMapping(value = "/tasks/delete/name/{name}", method = RequestMethod.DELETE)
    public void deleteByName(@PathVariable(value = "name") String name) {
        journal.delete(name);
    }

    /**
     * DELETE method for delete by id
     *
     * @param id
     */
    @RequestMapping(value = "/tasks/delete/id/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable(value = "name") String id) {
        journal.delete(UUID.fromString(id));
    }

    /**
     * DELETE method for delete by status
     *
     * @param status
     */
    @RequestMapping(value = "/tasks/delete/status/{status}", method = RequestMethod.DELETE)
    public void deleteByStatus(@PathVariable(value = "status") String status) {
        journal.delete(Action.valueOf(status.toUpperCase()));
    }

    /**
     * DELETE method for delete by period of time
     *
     * @param from
     * @param to
     * @throws ParseException
     */
    @RequestMapping(value = "/tasks/delete/period", method = RequestMethod.DELETE)
    public void deleteByPeriodOfTime(@RequestParam(value = "from") String from,
                                     @RequestParam(value = "to") String to) throws ParseException {
        Date fromDate = dateFormat.parse(from);
        Date toDate = dateFormat.parse(to);
        journal.delete(fromDate, toDate);
    }


    /**
     * POST method for create the Task
     *
     * @param name
     * @param describe
     * @param targetTime
     * @param reaction
     * @param contacts
     * @return Task
     * @throws ParseException
     */
    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public @ResponseBody
    Task addTasks(@RequestParam(value = "name") String name,
                  @RequestParam(value = "describe") String describe,
                  @RequestParam(value = "targetTime") String targetTime,
                  @RequestParam(value = "reaction") String reaction,
                  @RequestParam(value = "contacts", required = false) String contacts) throws ParseException {

        Date time = dateFormat.parse(targetTime);

        if (time.before(Date.from(Instant.now())))
            throw new IllegalArgumentException("Setting date before now.");

        Reaction action = Create.parseReaction(reaction);
        String[] communications = contacts.split(",");
        Task task = new Task(name, describe, time, action, Arrays.asList(communications));
        journal.add(task);
        return task;
    }

    /*@RequestMapping(value = "/tasks/set" , method = RequestMethod.PUT)
    public void putName(@RequestParam(value = "name") String name,
                        @RequestParam(value = "id") String id) {
        journal.findById(UUID.fromString(id)).forEach((task) -> task.setName(name));
    }

    @RequestMapping(value = "/tasks/set", method = RequestMethod.PUT)
    public void putDescribe(@RequestParam(value = "describe") String describe,
                            @RequestParam(value = "id") String id) {
        journal.findById(UUID.fromString(id)).forEach((task) -> task.setDescribe(describe));
    }

    @RequestMapping(value = "/tasks/set", method = RequestMethod.PUT)
    public void putTargetTime(@RequestParam(value = "targetTime") String targetTime,
                              @RequestParam(value = "id") String id) {
        journal.findById(UUID.fromString(id)).forEach((task) -> {
            try {
                task.setTargetTime(dateFormat.parse(targetTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @RequestMapping(value = "/tasks/set", method = RequestMethod.PUT)
    public void putReaction(@RequestParam(value = "describe") String reaction,
                            @RequestParam(value = "id") String id) {
        journal.findById(UUID.fromString(id)).forEach((task) -> task.setReaction(Create.parseReaction(reaction)));

    }*/


    @RequestMapping(value = "/tasks/{id}/{contacts}", method = RequestMethod.PUT)
    public @ResponseBody
    Task putContacts(@PathVariable(value = "id") String id, @PathVariable(value = "contacts") String contacts) {
        String[] communications = contacts.split(",");
        Task task = journal.findById(UUID.fromString(id)).get(0);
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
