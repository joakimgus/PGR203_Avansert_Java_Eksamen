package no.kristiania.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.net.URLDecoder;

public class HttpServer {

    private File contentRoot;
    private final List<String> memberNames = new ArrayList<>();
    private final List<String> emailAddresses = new ArrayList<>();

    public HttpServer(int port) throws IOException {
        // Opens a entry point to our program for network clients
        ServerSocket serverSocket = new ServerSocket(port);

        // New Threads that executes code in a separate "thread"
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    handleRequest(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start(); // Start the threads, so the code inside executes without blocking the current thread
    }

    private void handleRequest(Socket clientSocket) throws IOException {
        HttpMessage request = new HttpMessage(clientSocket);
        String requestLine = request.getStartLine();
        System.out.println(requestLine);

        String requestMethod = requestLine.split(" ")[0];
        String requestTarget = requestLine.split(" ")[1];
        String statusCode = "200";
        String body = "Hello <strong>World</strong>!";

        int questionPos = requestTarget.indexOf('?');

        String requestPath = questionPos != -1 ? requestTarget.substring(0, questionPos) : requestTarget;

        if (requestMethod.equals("POST")) {
            QueryString queryString = new QueryString(request.getBody());
            memberNames.add(queryString.getParameter("full_name"));
            emailAddresses.add(queryString.getParameter("email_address"));
            body = "Okay";

            String response = "HTTP/1.1 200 OK\r\n\r\n" +
                    "Content-Length: " + body.length() + "\r\n" +
                    "\r\n" +
                    body;
            clientSocket.getOutputStream().write(response.getBytes());
            return;
        }

        if (requestTarget.equals("/api/projectMembers")) {
            body = "<ol>";

            // For-each : member name
            for (String memberName : memberNames) {
                body += "<li>" + memberName + "</li>";
            }

            body += "</ol><ol>";

            // For-each : email address
            for ( String emailAddress : emailAddresses) {

                // Used URLDecoder for fixing problem with %40 => @
                body += "<li>" + URLDecoder.decode(emailAddress, "UTF-8") + "</li>";
            }
            body += "</ol>";

        } else if (questionPos != -1) {
            QueryString queryString = new QueryString(requestTarget.substring(questionPos+1));
            if (queryString.getParameter("status") != null) {
                statusCode = queryString.getParameter("status");
            }
            if (queryString.getParameter("body") != null) {
                body = queryString.getParameter("body");
            }
        } else if (!requestPath.equals("/echo")) {
            File file = new File(contentRoot, requestPath);
            if (!file.exists()) {
                body = file + " does not exist";
                String response = "HTTP/1.1 404 Not Found\r\n" +
                        "Content-Length: " + body.length() + "\r\n" +
                        "\r\n" +
                        body;
                // Write the response back to the client
                clientSocket.getOutputStream().write(response.getBytes());
                return;
            }
            statusCode = "200";
            String contentType = "text/plain";
            if (file.getName().endsWith(".html")) {
                contentType = "text/html";
            } else if (file.getName().endsWith(".css")){
                contentType = "text/css";
            }
            String response = "HTTP/1.1 " + statusCode + " OK\r\n" +
                    "Content-Length: " + file.length() + "\r\n" +
                    "Content-Type: " + contentType + "\r\n" +
                    "\r\n";
            clientSocket.getOutputStream().write(response.getBytes());

            new FileInputStream(file).transferTo(clientSocket.getOutputStream());
        }

        String response = "HTTP/1.1 " + statusCode + " OK\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "Content-Type: text/plain\r\n" +
                "\r\n" +
                body;

        clientSocket.getOutputStream().write(response.getBytes());
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer(8080);
        server.setContentRoot(new File("src/main/resources"));
    }

    public void setContentRoot(File contentRoot) {
        this.contentRoot = contentRoot;
    }

    public List<String> getMemberNames() {
        return memberNames;
    }

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }
}