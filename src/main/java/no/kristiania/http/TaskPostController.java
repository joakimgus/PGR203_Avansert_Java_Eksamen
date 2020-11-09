package no.kristiania.http;

import no.kristiania.database.Task;
import no.kristiania.database.TaskDao;

import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class TaskPostController implements HttpController {
    private final TaskDao taskDao;

    public TaskPostController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {

        QueryString requestParameter = new QueryString(request.getBody());

        Task task = new Task();
        String taskTitle = task.setTitle(requestParameter.getParameter("taskTitle"));
        task.setTitle(URLDecoder.decode(taskTitle, StandardCharsets.UTF_8));
        String taskDescription = task.setDescription(requestParameter.getParameter("taskDescription"));
        task.setDescription(URLDecoder.decode(taskDescription, StandardCharsets.UTF_8));
        taskDao.insert(task);

        String body = "Task " + "added." + "\r\n";
        String response = "HTTP/1.1 302 Found\r\n" +
                "Location: http://localhost:8080/index.html\r\n" +
                "Connection: close\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "\r\n" +
                body;
        clientSocket.getOutputStream().write(response.getBytes());
    }
}
