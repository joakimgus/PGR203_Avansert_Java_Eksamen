package no.kristiania.http;

import no.kristiania.database.*;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class HttpServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private final Map<String, HttpController> getControllers;
    private final Map<String, HttpController> postControllers;

    private MemberDao memberDao;
    private ServerSocket serverSocket;

    public HttpServer(int port, DataSource dataSource) throws IOException {
        memberDao = new MemberDao(dataSource);
        TaskDao taskDao = new TaskDao(dataSource);
        MemberTaskDao memberTaskDao = new MemberTaskDao(dataSource);
        TaskMemberDao taskMemberDao = new TaskMemberDao(dataSource);
        StatusDao statusDao = new StatusDao(dataSource);
        postControllers = Map.of(
                "/api/tasks", new TaskPostController(taskDao),
                "/api/newStatus", new StatusPostController(statusDao),
                "/api/addStatusToTask", new UpdateTaskController(taskDao),
                "/api/addMemberTask", new addMembersWithTasksPostController(memberDao, taskDao, memberTaskDao)
        );

        getControllers = Map.of(
                "/api/tasks", new TaskGetController(taskDao),
                "/api/statusOptions", new StatusOptionsController(statusDao),
                "/api/taskOptions", new TaskOptionsController(taskDao),
                "/api/memberOptions", new MemberOptionsController(memberDao),
                "/api/addMemberTask", new TasksWithMembersGetController(taskMemberDao),
                "/api/addTaskMember", new MembersWithTasksGetController(memberTaskDao)
        );

        serverSocket = new ServerSocket(port);
        logger.info("Server started on port {}", serverSocket.getLocalPort());

        new Thread(() -> {
            while (true) {
                try (Socket clientSocket = serverSocket.accept()){
                        handleRequest(clientSocket);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    private void handleRequest(Socket clientSocket) throws IOException, SQLException {
        HttpMessage request = new HttpMessage(clientSocket);
        String requestLine = request.getStartLine();
        System.out.println("REQUEST " + requestLine);

        String requestMethod = requestLine.split(" ")[0];

        String requestTarget = requestLine.split(" ")[1];

        int questionPos = requestTarget.indexOf('?');

        String requestPath = questionPos != -1 ? requestTarget.substring(0, questionPos) : requestTarget;

        if (requestMethod.equals("POST")) {
            if (requestPath.equals("/api/members")) {
                handlePostMember(clientSocket, request);
            } else {
                getController(requestPath).handle(request, clientSocket);
            }
        } else {
            if (requestPath.equals("/")) {
                handleSlashRequest(clientSocket);
            } else if (requestPath.equals("/echo")) {
                handleEchoRequest(clientSocket, requestTarget, questionPos);
            } else if (requestPath.equals("/api/members")) {
                handleGetMembers(clientSocket);
            } else {
                HttpController controller = getControllers.get(requestPath);
                if (controller != null) {
                    controller.handle(request, clientSocket);
                } else {
                    handleFileRequest(clientSocket, requestPath);
                }
            }
        }
    }

    private HttpController getController(String requestPath) {
        return postControllers.get(requestPath);
    }

    private void handlePostMember(Socket clientSocket, HttpMessage request) throws SQLException, IOException {
        QueryString requestParameter = new QueryString(request.getBody());

        Member member = new Member();
        String fullName = member.setName(requestParameter.getParameter("full_name"));
        member.setName(URLDecoder.decode(fullName, "UTF-8"));
        String emailAddress = member.setEmail(requestParameter.getParameter("email_address"));
        member.setEmail(URLDecoder.decode(emailAddress, "UTF-8"));
        memberDao.insert(member);
        String body = "Member " + URLDecoder.decode(fullName, "UTF-8") + " added." + "\r\n";
        String response = "HTTP/1.1 302 Found\r\n" +
                "Location: http://localhost:8080/index.html\r\n" +
                "Connection: close\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "\r\n" +
                body;
        clientSocket.getOutputStream().write(response.getBytes());
    }

    private void handleSlashRequest(Socket clientSocket) throws IOException {
        String body = "Redirecting to index";
        String response = "HTTP/1.1 302 Redirect\r\n" +
                "Location: /index.html\r\n" +
                "Connection: close\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "\r\n" +
                body;
        clientSocket.getOutputStream().write(response.getBytes());
    }

    private void handleFileRequest(Socket clientSocket, String requestPath) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream(requestPath)) {
            if (inputStream == null) {
                String body = requestPath + " does not exist";
                String response = "HTTP/1.1 404 Not Found\r\n" +
                        "Content-Length: " + body.length() + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n" +
                        body;
                clientSocket.getOutputStream().write(response.getBytes());
                return;
            }
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            inputStream.transferTo(buffer);

            String contentType = "text/plain";
            if (requestPath.endsWith(".html")) {
                contentType = "text/html";
            } else if (requestPath.endsWith(".css")){
                contentType = "text/css";
            }

            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + buffer.toByteArray().length + "\r\n" +
                    "Connection: close\r\n" +
                    "Content-Type: " + contentType + "\r\n" +
                    "\r\n";
            clientSocket.getOutputStream().write(response.getBytes());
            clientSocket.getOutputStream().write(buffer.toByteArray());
        }
    }


    /* Endre visning av medlemmer og oppgaver gitt til medlemmer her */
    private void handleGetMembers(Socket clientSocket) throws IOException, SQLException {
        String body = "<ul>";
        for (Member member : memberDao.list()) {
                body += "<li>" + "Name: " + member.getName() + "<br>Email: " + member.getEmail() + "<br>______________________________<br>" + "</li>";
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

    private void handleEchoRequest(Socket clientSocket, String requestTarget, int questionPos) throws IOException {
        String statusCode = "200";
        String body = "Hello <strong>World</strong>!";
        if (questionPos != -1) {
            QueryString queryString = new QueryString(requestTarget.substring(questionPos + 1));
            if (queryString.getParameter("status") != null) {
                statusCode = queryString.getParameter("status");
            }
            if (queryString.getParameter("body") != null) {
                body = queryString.getParameter("body");
            }
        }
        String response = "HTTP/1.1 " + statusCode + " OK\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "Content-Type: text/plain\r\n" +
                "\r\n" +
                body;

        clientSocket.getOutputStream().write(response.getBytes());
    }

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader("pgr203.properties")) {
            properties.load(fileReader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        logger.info("Using database {}", dataSource.getUrl());
        Flyway.configure().dataSource(dataSource).load().migrate();

        HttpServer server = new HttpServer(8080, dataSource);
        logger.info("Started on http://localhost:{}/index.html", 8080);
    }

    public List<Member> getMembers() throws SQLException {
        return memberDao.list();
    }
}