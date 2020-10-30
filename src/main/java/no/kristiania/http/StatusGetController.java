package no.kristiania.http;

import no.kristiania.database.Status;
import no.kristiania.database.StatusDao;

import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
import java.sql.SQLException;

public class StatusGetController implements HttpController{
    private StatusDao statusDao;

    public StatusGetController(StatusDao statusDao) {
            this.statusDao = statusDao;
        }

        @Override
        public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
            String body = "<ul>";
            for (Status status : statusDao.list()) {
                body += "<li>" + URLDecoder.decode(status.getName(), "utf-8") + "</li>";
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
