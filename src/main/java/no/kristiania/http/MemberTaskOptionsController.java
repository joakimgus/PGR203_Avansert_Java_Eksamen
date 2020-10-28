package no.kristiania.http;

import no.kristiania.database.MemberTask;
import no.kristiania.database.MemberTaskDao;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class MemberTaskOptionsController implements HttpController {
    private MemberTaskDao taskDao;

    public MemberTaskOptionsController(MemberTaskDao memberTaskDao) {
        this.taskDao = memberTaskDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        HttpMessage response = new HttpMessage(getBody());
        response.write(clientSocket);
    }

    public String getBody() throws SQLException {
        String body = "";
        for (MemberTask task : taskDao.list()) {
            body += "<option value=" + task.getId() + ">" + task.getTitle() + "</option>";
        }
        return body;
    }
}
