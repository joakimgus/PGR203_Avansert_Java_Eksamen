package no.kristiania.innlevering2;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {

    private final Map<String, String> headers = new HashMap<>();
    private final String startLine;
    private String body;

    public HttpMessage(Socket socket) throws IOException {
        startLine = HttpMessage.readLine(socket);
        HttpMessage.readHeaders(headers, socket);
        if (headers.containsKey("Content-Length")) {
            body = readBody(socket, Integer.parseInt(headers.get("Content-Length")));
        }

    }

    public String getStartLine() {
        return startLine;
    }

    public String getBody() {
        return body;
    }

    public static String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            // Each line ends with \r\n (CRLF - carriage return, line feed)
            if (c == '\r') {
                socket.getInputStream().read(); // Read and ignore the following \n
                break;
            }
            line.append((char)c);
        }
        return line.toString();
    }

    static void readHeaders(Map<String, String> responseHeaders, Socket socket) throws IOException {
        String headerLine;
        while (!(headerLine = readLine(socket)).isEmpty()) {
            int colonPos = headerLine.indexOf(':');

            // Parse header
            String headerName = headerLine.substring(0, colonPos);
            String headerValue = headerLine.substring(colonPos+1).trim();

            // Store headers
            responseHeaders.put(headerName, headerValue);
        }
    }

    static String readBody(Socket socket, int contentLength) throws IOException {
        // Response header content-length tells how many bytes the response body is
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            // Read content body based on content-length
            body.append((char) socket.getInputStream().read());
        }
        return body.toString();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
