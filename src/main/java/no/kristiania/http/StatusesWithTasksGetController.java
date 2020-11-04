package no.kristiania.http;

import no.kristiania.database.*;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class StatusesWithTasksGetController implements HttpController{
    private TaskStatusDao taskStatusDao;

    public StatusesWithTasksGetController(TaskStatusDao taskStatusDao) {
        this.taskStatusDao = taskStatusDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        String body = "<ul>";
        List<MemberTask> taskStatus = taskStatusDao.list();
        body += "<li>Status: " + taskStatus.get(0).getStatusName() + "<br>" +
                "<br>Task: " + taskStatus.get(0).getTaskTitle() +
                "<br>Member: " + taskStatus.get(0).getMemberName() + "<br></li>";

        for ( int i = 1; i < taskStatus.size(); i++) {
            if ( taskStatus.get(i).getStatusName().equals(taskStatus.get(i-1).getStatusName())) {
                body += "<br>Task: " + taskStatus.get(i).getTaskTitle() +
                        "<br>Member: " + taskStatus.get(i).getMemberName() + "</li><br>";
            } else {
                body += "____________________________________<br>";
                body += "<li>Status: " + taskStatus.get(i).getStatusName() + "<br>" +
                        "<br>Task: " + taskStatus.get(i).getTaskTitle() +
                        "<br>Member: " + taskStatus.get(i).getMemberName() + "</li>";
            }
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
