package no.kristiania.http;

import no.kristiania.database.MemberTask;
import no.kristiania.database.MemberTaskDao;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class MembersWithTasksPostController implements HttpController{
    private final MemberTaskDao memberTaskDao;

    public MembersWithTasksPostController(MemberTaskDao memberTaskDao) {
        this.memberTaskDao = memberTaskDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        QueryString requestParameter = new QueryString(request.getBody());

        MemberTask memberTask = new MemberTask();
        memberTask.setMemberId(Integer.parseInt(requestParameter.getParameter("memberId")));
        memberTask.setTaskId(Integer.parseInt(requestParameter.getParameter("taskId")));
        memberTaskDao.insert(memberTask);

        String body = "Okay\r\n";
        String response = "HTTP/1.1 302 Found\r\n" +
                "Location: http://localhost:8080/index.html\r\n" +
                "Connection: close\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "\r\n" +
                body;
        clientSocket.getOutputStream().write(response.getBytes());
    }
}