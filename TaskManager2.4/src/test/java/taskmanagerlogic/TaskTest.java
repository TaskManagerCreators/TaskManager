package taskmanagerlogic;

import com.reaction.Output;
import com.taskmanagerlogic.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpRequest;
import com.reaction.Output;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


class TaskTest {

    private Task task;


    @Before
    public void initTest() {

    }

    @After
    public void afterTest() {
        task = null;
    }

    @Test
    public void testOutput() {
        task = new Task("task", "purpose", new Date(), new Output("test"),
                new ArrayList<>(Arrays.asList(new String[]{"Kate", "Paul"})));
        String out = task.toString();
        assertNotEquals(null, out);
    }

    @Test
    public void test() throws IOException {
        String url = "http://localhost:8080/tasks/name/test";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
    }
}