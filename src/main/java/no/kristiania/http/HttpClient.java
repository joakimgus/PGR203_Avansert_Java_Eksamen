package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class HttpClient {

    private final int statusCode;
    private final Map<String, String> responseHeaders;
    String responseBody;

    public HttpClient(final String hostname, int port, final String requestTarget) throws IOException {
        this(hostname, port, requestTarget, "GET", null);
    }

    public HttpClient(final String hostname, int port, final String requestTarget, final String httpMethod, String requestBody) throws IOException {
        // Connect to server
        Socket socket = new Socket(hostname, port);

        String contentLengthHeader = requestBody != null ? "Content-Length: " + requestBody.length() + "\r\n" : "";

        String request = httpMethod + " " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + hostname + "\r\n" +
                contentLengthHeader +
                // Request ends with empty line
                "\r\n";

        // Send request to server
        socket.getOutputStream().write(request.getBytes());

        // Checks if body contains any bytes
        if (requestBody != null) {
            socket.getOutputStream().write(requestBody.getBytes());
        }

        HttpMessage httpMessage = new HttpMessage(socket);

        String responseLine = httpMessage.getStartLine();
        String[] responseLineParts = responseLine.split(" ");

        // Status code determines if it went ok or not.
        statusCode = Integer.parseInt(responseLineParts[1]);
        responseHeaders = httpMessage.getHeaders();
        responseBody = httpMessage.getBody();
    }

    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?status=404&Content-Type=text%2Fhtml&body=Hello+world");
        System.out.println(client.getResponseBody());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseHeader(String headerName) {
        return responseHeaders.get(headerName);
    }

    public String getResponseBody() {
        return responseBody;
    }
}
