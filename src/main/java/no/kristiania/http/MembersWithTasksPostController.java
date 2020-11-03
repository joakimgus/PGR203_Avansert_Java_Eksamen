package no.kristiania.http;

import no.kristiania.database.MemberDao;
import no.kristiania.database.MemberTask;
import no.kristiania.database.MemberTaskDao;
import no.kristiania.database.TaskDao;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class MembersWithTasksPostController implements HttpController{
    private final MemberDao memberDao;
    private final TaskDao taskDao;
    private final MemberTaskDao memberTaskDao;

    public MembersWithTasksPostController(MemberDao memberDao, TaskDao taskDao, MemberTaskDao memberTaskDao) {
        this.memberDao = memberDao;
        this.taskDao = taskDao;
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