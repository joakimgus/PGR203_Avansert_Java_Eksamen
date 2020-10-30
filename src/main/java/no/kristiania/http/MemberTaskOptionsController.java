package no.kristiania.http;

import no.kristiania.database.MemberTask;
import no.kristiania.database.MemberTaskDao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
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

    public String getBody() throws SQLException, UnsupportedEncodingException {
        String body = "";
        for (MemberTask task : taskDao.list()) {
            body += "<option value=" + task.getId() + ">" + URLDecoder.decode(task.getTitle(), "utf-8") + "</option>";
        }
        return body;
    }
}
