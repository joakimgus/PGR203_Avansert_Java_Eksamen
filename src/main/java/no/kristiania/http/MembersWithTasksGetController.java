package no.kristiania.http;

import no.kristiania.database.MemberTask;
import no.kristiania.database.MemberTaskDao;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class MembersWithTasksGetController implements HttpController {

        private final MemberTaskDao memberTaskDao;

        public MembersWithTasksGetController(MemberTaskDao memberTaskDao) {
            this.memberTaskDao = memberTaskDao;
        }

        @Override
        public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
            String body = "<ul>";
            List<MemberTask> memberTasks = memberTaskDao.list();
            body +=  "<li>Member: " + memberTasks.get(0).getMemberName() +
                    "<br>Email: " + memberTasks.get(0).getMemberEmail() +
                    "<br>Assigned task:<br>" + memberTasks.get(0).getTaskTitle() + " | " + memberTasks.get(0).getStatusName() + "<br></li>";

            for ( int i = 1; i < memberTasks.size(); i++) {
                if ( memberTasks.get(i).getMemberName().equals(memberTasks.get(i-1).getMemberName())) {
                    body += memberTasks.get(i).getTaskTitle() + " | " + memberTasks.get(i).getStatusName() + "</li><br>";
                } else {
                    body += "_________________________________<br>";
                    body += "<li>Member: " + memberTasks.get(i).getMemberName() +
                            "<br>Email: " + memberTasks.get(i).getMemberEmail() +
                            "<br>Assigned tasks:<br> " + memberTasks.get(i).getTaskTitle() + " | " + memberTasks.get(i).getStatusName() + "</li>";
                }
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
