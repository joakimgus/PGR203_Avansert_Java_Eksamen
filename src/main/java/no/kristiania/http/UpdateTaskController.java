package no.kristiania.http;

import no.kristiania.database.MemberTask;
import no.kristiania.database.MemberTaskDao;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class UpdateTaskController implements HttpController {

    private MemberTaskDao memberTaskDao;

    public UpdateTaskController(MemberTaskDao memberTaskDao) {
        this.memberTaskDao = memberTaskDao;
    }

    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        HttpMessage response = handle(request);
        response.write(clientSocket);
    }

    public HttpMessage handle(HttpMessage request) throws SQLException {
        QueryString requestParameter = new QueryString(request.getBody());

        Integer id = Integer.valueOf(requestParameter.getParameter("id"));
        Integer statusId = Integer.valueOf(requestParameter.getParameter("statusId"));
        MemberTask memberTask = memberTaskDao.retrieve(id);
        /*memberTask.setStatusId(statusId);

        memberTaskDao.update(memberTask);
*/
        HttpMessage redirect = new HttpMessage();
        redirect.setStartLine("HTTP/1.1 302 Redirect");
        redirect.getHeaders().put("Location", "http://localhost:8080/index.html");
        return redirect;
    }
}
