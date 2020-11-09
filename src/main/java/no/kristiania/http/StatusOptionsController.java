package no.kristiania.http;

import no.kristiania.database.Status;
import no.kristiania.database.StatusDao;

import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class StatusOptionsController implements HttpController{

    private final StatusDao statusDao;

    public StatusOptionsController(StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        HttpMessage response = new HttpMessage(getBody());
        response.write(clientSocket);
    }

    public String getBody() throws SQLException {
        String body = "";
        for (Status status : statusDao.list()) {
            body += "<option value=" + status.getId() + ">" + URLDecoder.decode(status.getName(), StandardCharsets.UTF_8) + "</option>";
        }
        return body;
    }
}
