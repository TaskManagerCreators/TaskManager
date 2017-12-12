package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import commands.Create;
import config.TaskManagerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonFactoryBean;
import taskmanagerlogic.Cleaner;
import taskmanagerlogic.CommandResolver;
import taskmanagerlogic.InterAction;
import taskmanagerlogic.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskControllerTest {
    @Test
    void getTasksByName() throws IOException {

        String request = "http://localhost:8080/tasks/name/test";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        assertEquals(connection.getResponseCode() , 200);



    }

    @Test
    void getTasksByStatus() throws IOException {
        String request = "http://localhost:8080/tasks/status/scheduled";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        assertEquals(connection.getResponseCode() , 200);
    }

    @Test
    void getTasksByPeriodOfTime() throws IOException {
        String request = "http://localhost:8080/tasks/date/?from=20:00%2012.01.2011&to=20:00%2012.12.2020";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        assertEquals(connection.getResponseCode() , 200);
    }

    @Test
    void getTasksById() throws IOException {
        String request = "http://localhost:8080/tasks/id/7e0f841f-2dc0-4613-bf54-57f36c8149ca";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        assertEquals(connection.getResponseCode() , 200);
    }

    @Test
    void getHistory() throws IOException {
        String request = "http://localhost:8080/tasks/history";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        assertEquals(connection.getResponseCode() , 200);
    }

    /*@Test
    void deleteByName() throws IOException {
        String req ="http://localhost:8080/tasks/?name=test2&desc=desc&targetTime=20:00 20.12.2020&reaction=output-asd";
        URL url1 = new URL(req);
        HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
        connection1.setRequestMethod("DELETE");
        String request = "http://localhost:8080/tasks/delete/name/test2";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        assertEquals(connection.getResponseCode() , 202);
    }


    @Test
    void deleteByStatus() throws IOException {
        String request = "http://localhost:8080/tasks/delete/status/scheduled";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        String req ="http://localhost:8080/tasks/?name=test2&desc=desc&targetTime=20:00 20.12.2020&reaction=output-asd";
        URL url1 = new URL(req);
        HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
        connection1.setRequestMethod("POST");
        assertEquals(connection.getResponseCode() , 202);
    }

    @Test
    void deleteByPeriodOfTime() throws IOException {
        String request = "http://localhost:8080/tasks/delete/period/?from=20:00 10.02.2010&to=20.12.2020";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        String req ="http://localhost:8080/tasks/?name=test2&desc=desc&targetTime=20:00 20.12.2020&reaction=output-asd";
        URL url1 = new URL(req);
        HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
        connection1.setRequestMethod("POST");
        assertEquals(connection.getResponseCode() , 202);
    }

    @Test
    void addTasks() throws IOException {
        String req ="http://localhost:8080/tasks/?name=tests&desc=desc&targetTime=20:00 20.12.2020&reaction=output-asd";
        URL url = new URL(req);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        assertEquals(connection.getResponseCode() , 201);
    }*/
}