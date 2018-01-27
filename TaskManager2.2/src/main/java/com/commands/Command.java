package com.commands;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.zip.DataFormatException;

/**
 * Encapsulates each user-entered command
 *
 * @see Create
 * @see Show
 * @see Delete
 * @see Clean
 * @see Help
 * @see Show
 * @see ShowHistory
 */
@Component
public interface Command extends Runnable {

    String datePattern = "^((([1 - 12]):(((0[0-9])|([1-5][0-9]))) ((am)|(pm)))|" +
            "((0?[0-9]|1[0-9]|2[0-3]):((0[0-9])|([1-5][0-9])))) " +
            "((0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).(20\\d\\d))$";

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");

    String state = "s", id = "id", name = "n", date = "d";

    void execute(String ... args) throws ParseException, DataFormatException, IOException;

}
