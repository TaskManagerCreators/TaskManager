package commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.zip.DataFormatException;


public interface Command {

    String datePattern = "^((([1 - 12]):(((0[0-9])|([1-5][0-9]))) ((am)|(pm)))|" +
            "((0?[0-9]|1[0-9]|2[0-3]):((0[0-9])|([1-5][0-9])))) " +
            "((0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).(20\\d\\d))$";

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");

    String state = "s", id = "id", name = "n", date = "d";

    /*EnumMap<Keys, String> keys = new EnumMap<Keys, String>(Keys.class) {{
        put(Keys.STATUS, "s");
        put(Keys.NAME, "n");
        put(Keys.ID, "id");
        put(Keys.DATE, "d");
    }};*/

    void execute(String command) throws ParseException, DataFormatException;
}
