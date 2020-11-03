package no.kristiania.http;

import no.kristiania.database.MemberTask;
import no.kristiania.database.MemberTaskDao;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class MembersWithTasksGetController implements HttpController {

        private MemberTaskDao memberTaskDao;

        public MembersWithTasksGetController(MemberTaskDao memberTaskDao) {
            this.memberTaskDao = memberTaskDao;
        }

        @Override
        public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
            String body = "<div>";
            List<MemberTask> memberTasks = memberTaskDao.list();
            body +=  memberTasks.get(0).getMemberName() + "<br> Email: " + memberTasks.get(0).getMemberEmail() + "<br> Task Title: " + memberTasks.get(0).getTaskTitle() + "<br> Status: " + memberTasks.get(0).getStatusName();
            for ( int i = 1; i < memberTasks.size(); i++) {
                if ( memberTasks.get(i).getMemberName().equals(memberTasks.get(i-1).getMemberName())) {
                    body += "<br> Task Title: " + memberTasks.get(i).getTaskTitle() + "<br> Status: " + memberTasks.get(0).getStatusName();
                } else {
                    body += "<br> ------------------------------------";
                    body += "<br>" + memberTasks.get(i).getMemberName() + "<br> Email: " + memberTasks.get(i).getMemberEmail() + "<br> Task Title: " + memberTasks.get(i).getTaskTitle() + "<br> Status: " + memberTasks.get(0).getStatusName();
                }
            }

            body += "</div>";
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + body.getBytes().length + "\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    body;

            clientSocket.getOutputStream().write(response.getBytes());
        }
}
