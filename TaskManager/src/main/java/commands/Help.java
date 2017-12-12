package commands;

import java.text.ParseException;
import java.util.zip.DataFormatException;

public class Help implements Command {

    @Override
    public void execute(String command) throws ParseException, DataFormatException {
        System.out.println("This is help. Please , write correctly");
    }

    @Override
    public void run() {
        try {
            execute(null);
        } catch (ParseException | DataFormatException e) {
            e.printStackTrace();
        }
    }
}
