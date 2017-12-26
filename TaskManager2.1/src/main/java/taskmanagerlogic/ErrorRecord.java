package taskmanagerlogic;

import java.util.Date;
import java.util.UUID;

/**
 * This is a record of a task error.
 *
 * @version 1.1
 */
public class ErrorRecord {
    private UUID id;
    private Class exceptionClass;
    private String message;
    private StackTraceElement[] stackTrace;
    private Date date;

    public ErrorRecord(UUID id, Class exceptionClass, String message, StackTraceElement[] stackTrace, Date date) {
        this.id = id;
        this.exceptionClass = exceptionClass;
        this.message = message;
        this.date = date;
        this.stackTrace = stackTrace;
    }
}