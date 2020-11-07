package no.kristiania.http;

import no.kristiania.database.Task;
import no.kristiania.database.TaskDao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class TaskOptionsController implements HttpController {
    private final TaskDao taskDao;

    public TaskOptionsController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        HttpMessage response = new HttpMessage(getBody());
        response.write(clientSocket);
    }

    public String getBody() throws SQLException {
        String body = "";
        for (Task task : taskDao.list()) {
            body += "<option value=" + task.getId() + ">" + URLDecoder.decode(task.getTitle(), StandardCharsets.UTF_8) + "</option>";
        }
        return body;
    }
}
