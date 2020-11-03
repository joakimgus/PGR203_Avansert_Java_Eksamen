package no.kristiania.http;

import no.kristiania.database.*;

import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

public class addMembersWithTasksGetController implements HttpController{
    private MemberDao memberDao;
    private TaskDao taskDao;
    private MemberTaskDao memberTaskDao;

    public addMembersWithTasksGetController(MemberDao memberDao, TaskDao taskDao, MemberTaskDao memberTaskDao) {
        this.memberDao = memberDao;
        this.taskDao = taskDao;
        this.memberTaskDao = memberTaskDao;
    }


    @Override
    public void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException {
        String body = "<ul>";

        /*
        for(MemberTask memberTask : memberTaskDao.list()) {
            body += "<li>" + "Title: " + memberTask.getTaskTitle() + "<br> Description: " + memberTask.getTaskDescription()
            if ()
                body+="<br> Member: " + memberTask.getMemberName() + "<br> ------------------------------------- <br>";
        }
        */
        List<MemberTask> memberTasks = memberTaskDao.list();
        for ( int i = 0; i < memberTasks.size(); i++) {
            if( i > 0 && memberTasks.get(i-1).getTaskTitle() != memberTasks.get(i).getTaskTitle() ) {
                body += "<li>" + "Title: " + memberTasks.get(i).getTaskTitle() + "<br> Description: " + memberTasks.get(i).getTaskDescription() + "<br> Member: " + memberTasks.get(i).getMemberName();
            } else {
                body += "<br> Member" + memberTasks.get(i).getMemberName();
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