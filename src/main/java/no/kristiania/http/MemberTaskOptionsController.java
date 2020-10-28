package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class MemberTaskOptionsController implements HttpController {
    private MemberTaskDao memberTaskDao;

    public MemberTaskOptionsController(MemberTaskDao memberTaskDao) {
        this.memberTaskDao = memberTaskDao;
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
