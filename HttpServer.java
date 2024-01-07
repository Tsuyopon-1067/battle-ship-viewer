import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        int port = 50001;
        String rootDirectory = "./";

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("HTTP File Server listening on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleRequest(clientSocket, rootDirectory);
                }
            }
        }
    }

    private static void handleRequest(Socket clientSocket, String rootDirectory) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream output = clientSocket.getOutputStream()) {

            String request = reader.readLine();
            if (request != null && request.startsWith("GET")) {
                String[] parts = request.split(" ");
                String filePath = rootDirectory + parts[1];

                File file = new File(filePath);
                if (file.exists() && file.isFile()) {
                    String contentType = getContentType(filePath);
                    sendResponse(output, "HTTP/1.1 200 OK", contentType, file);
                } else {
                    sendResponse(output, "HTTP/1.1 404 Not Found", "text/plain", "File not found");
                }
            }
        }
    }

    private static void sendResponse(OutputStream output, String status, String contentType, Object content) throws IOException {
        PrintWriter writer = new PrintWriter(output);
        writer.println(status);
        writer.println("Content-Type: " + contentType);
        if (content instanceof File) {
            writer.println("Content-Length: " + ((File)content).length());
        } else if (content instanceof String) {
            writer.println("Content-Length: " + ((String) content).length());
        }
        writer.println();
        writer.flush();

        if (content instanceof File) {
            try (InputStream fileInput = new FileInputStream((File) content)) {
                byte[] buffer = new byte[2000000];
                int bytesRead;
                while ((bytesRead = fileInput.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            }
        } else {
            writer.println(content);
        }

        writer.flush();
        System.err.println(content);
    }

    private static String getContentType(String filePath) {
        if (filePath.endsWith(".ico")) return "image/x-icon";
        if (filePath.endsWith(".json")) return "application/json";
        if (filePath.endsWith(".png")) return "image/png";
        if (filePath.endsWith(".js")) return "application/javascript";
        if (filePath.endsWith(".html")) return "text/html";
        if (filePath.endsWith(".svg")) return "image/svg+xml";
        if (filePath.endsWith(".css")) return "text/css";
        return "text/plain";
    }
}

