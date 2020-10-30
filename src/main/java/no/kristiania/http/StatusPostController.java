package no.kristiania.http;

import no.kristiania.database.Status;
import no.kristiania.database.StatusDao;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class StatusPostController implements HttpController {
    private final StatusDao statusDao;

    public StatusPostController(StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {

        QueryString requestParameter = new QueryString(request.getBody());

        Status status = new Status();
        status.setName(requestParameter.getParameter("statusName"));
        statusDao.insert(status);

        String body = "Okay";
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body;

        clientSocket.getOutputStream().write(response.getBytes());
    }
}
