/*
package no.kristiania.http;

import no.kristiania.database.*;

import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
import java.sql.SQLException;

public class addMembersWithTasksGetController implements HttpController{
    private MemberDao memberDao;
    private TaskDao taskDao;
    private MemberTaskDao memberTaskDao;

    public addMembersWithTasksGetController(MemberDao memberDao, TaskDao taskDao, MemberTaskDao memberTaskDao) {
        this.memberDao = memberDao;
        this.taskDao = taskDao;
        this.memberTaskDao = memberTaskDao;
    }


    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        String body = "<ul>";
        for (Task task : taskDao.list()) {

            String taskId = "" + task.getId();
            String memberName = "";

            for (Member member : memberDao.list()) {
                memberName = memberName + member.getName();
            }

            String status = task
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
*/