package no.kristiania.http;

import no.kristiania.database.Task;
import no.kristiania.database.TaskDao;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class UpdateTaskController implements HttpController {

    private final TaskDao taskDao;

    public UpdateTaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        HttpMessage response = handle(request);
        response.write(clientSocket);
    }

    public HttpMessage handle(HttpMessage request) throws SQLException {
        QueryString requestParameter = new QueryString(request.getBody());

        Integer taskId = Integer.valueOf(requestParameter.getParameter("title"));
        Integer statusId = Integer.valueOf(requestParameter.getParameter("statusName"));
        Task task = taskDao.retrieve(taskId);
        task.setStatusId(statusId);

        taskDao.update(task);

        HttpMessage redirect = new HttpMessage();
        redirect.setStartLine("HTTP/1.1 302 Redirect");
        redirect.getHeaders().put("Location", "http://localhost:8080/index.html");
        return redirect;
    }
}
