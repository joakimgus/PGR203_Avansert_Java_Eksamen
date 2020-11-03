package no.kristiania.http;

import no.kristiania.database.*;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class addMembersWithTasksGetController implements HttpController{
    private final MemberTaskDao memberTaskDao;

    public addMembersWithTasksGetController(MemberTaskDao memberTaskDao) {
        this.memberTaskDao = memberTaskDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        String body = "<div>";
        List<MemberTask> memberTasks = memberTaskDao.list();

        body += "Title: " + memberTasks.get(0).getTaskTitle() +
                " | Status: " + memberTasks.get(0).getStatusName() +
                "<br> Description: " + memberTasks.get(0).getTaskDescription() +
                "<br>" + memberTasks.get(0).getMemberName();

        for ( int i = 1; i < memberTasks.size(); i++) {
            if ( memberTasks.get(i).getTaskTitle().equals(memberTasks.get(i-1).getTaskTitle())) {
                body += "<br>" + memberTasks.get(i).getMemberName();
            } else {
                body += "<br> ------------------------------------";
                body += "<br> Title: " + memberTasks.get(i).getTaskTitle() +
                        " | Status: " + memberTasks.get(i).getStatusName() +
                        "<br> Description: " + memberTasks.get(i).getTaskDescription() +
                        "<br>" + memberTasks.get(i).getMemberName();
            }
        }

        body += "</div>";
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + body.getBytes().length + "\r\n" +
                "Content-Type: text/html\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body;

        clientSocket.getOutputStream().write(response.getBytes());
    }
}