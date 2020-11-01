package no.kristiania.http;

import no.kristiania.database.Status;
import no.kristiania.database.StatusDao;

import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
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
        String statusName = status.setName(requestParameter.getParameter("statusName"));
        status.setName(URLDecoder.decode(statusName, "UTF-8"));
        statusDao.insert(status);

        String body = "Status " + "added." + "\r\n";
        String response = "HTTP/1.1 302 Found\r\n" +
                "Location: http://localhost:8080/index.html\r\n" +
                "Connection: close\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "\r\n" +
                body;

        clientSocket.getOutputStream().write(response.getBytes());
    }
}
