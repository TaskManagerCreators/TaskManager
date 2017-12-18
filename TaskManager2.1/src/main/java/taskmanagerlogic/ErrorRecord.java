package taskmanagerlogic;

import java.util.Date;
import java.util.UUID;

/**
 * This is a record of a task error.
 * Maybe this will be deleted.
 * @version 1.0
 */
public class ErrorRecord {
    private UUID id;
    private Class exceptionClass;
    private String message;
    private StackTraceElement[] stackTrace;
    private Task task;
    private Date date;
    public ErrorRecord(UUID id , Class exceptionClass , String message, StackTraceElement[] stackTrace , Task task , Date date){
        this.id = id;
        this.exceptionClass = exceptionClass;
        this.message = message;
        this.task = task;
        this.date = date;
        this.stackTrace = stackTrace;
    }
}
