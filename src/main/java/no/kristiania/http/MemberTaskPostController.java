package no.kristiania.http;

import java.io.IOException;
import java.sql.SQLException;

public class MemberTaskPostController implements HttpController {
    private MemberTaskDao memberTaskDao;

    public MemberTaskPostController(MemberTaskDao memberTaskDao) {
        this.memberTaskDao = memberTaskDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {

        QueryString requestParameter = new QueryString(request.getBody());

        MemberTask task = new MemberTask();
        task.setTitle(requestParameter.getParameter("taskTitle"));
        memberTaskDao.insert(task);

        String body = "Okay";
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body;

        clientSocket.getOutputStream().write(response.getBytes());
    }
}
