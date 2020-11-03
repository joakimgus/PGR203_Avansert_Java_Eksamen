package no.kristiania.http;

import no.kristiania.database.*;

import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

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

        for(MemberTask memberTask : memberTaskDao.list()) {
            body += "<li>" + "Title: " + memberTask.getTaskTitle() + "<br> Description: " + memberTask.getTaskDescription() + "<br> Member: " + memberTask.getMemberName() + "<br> ------------------------------------- <br>";
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