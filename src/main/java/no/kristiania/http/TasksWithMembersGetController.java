package no.kristiania.http;

import no.kristiania.database.*;

import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

public class TasksWithMembersGetController implements HttpController{
    private TaskMemberDao memberTaskDao;

    public TasksWithMembersGetController(TaskMemberDao memberTaskDao) {
        this.memberTaskDao = memberTaskDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        String body = "<div>";
        List<MemberTask> taskMembers = memberTaskDao.list();
        body += "Title: " + taskMembers.get(0).getTaskTitle() + "<br> Status: " + taskMembers.get(0).getStatusName() +"<br> Description: " + taskMembers.get(0).getTaskDescription() + "<br>" + taskMembers.get(0).getMemberName();
        for ( int i = 1; i < taskMembers.size(); i++) {
            if ( taskMembers.get(i).getTaskTitle().equals(taskMembers.get(i-1).getTaskTitle())) {
                body += "<br>" + taskMembers.get(i).getMemberName();
            } else {
                body += "<br> ------------------------------------";
                body += "<br> Title: " + taskMembers.get(i).getTaskTitle() + "<br> Status: " + taskMembers.get(i).getStatusName() + "<br> Description: " + taskMembers.get(i).getTaskDescription() + "<br>" + taskMembers.get(i).getMemberName();
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