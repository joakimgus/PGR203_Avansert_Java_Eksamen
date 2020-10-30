package no.kristiania.http;

import no.kristiania.database.Task;
import no.kristiania.database.TaskDao;

import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
import java.sql.SQLException;

public class TaskGetController implements HttpController {
    private TaskDao taskDao;

    public TaskGetController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        String body = "<ul>";
        for (Task task : taskDao.list()) {
            body += "<li>" + URLDecoder.decode(task.getTitle(), "UTF-8") + "<br>" + task.getDescription() + "</li>";
        }

        body += "</ul>";
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + body.getBytes().length + "\r\n" +
                "Content-Type: text/html\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body;

        clientSocket.getOutputStream().write(response.getBytes());
    }
}