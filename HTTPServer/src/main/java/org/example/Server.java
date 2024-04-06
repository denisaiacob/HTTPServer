package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class Server {

    private int port;
    private HashMap<String, RequestAllowed> paths = new HashMap<>();

    protected void setPort(int port) {
        this.port = port;
    }

    protected void addPath(String path, RequestAllowed requestAllowed) {
        paths.put(path, requestAllowed);
    }

    protected void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Handle client request in a new thread
                Thread clientHandler = new Thread(() -> handleRequest(clientSocket));
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream outputStream = clientSocket.getOutputStream()) {
            RequestHandler requestHandler = new RequestHandler();

            // Read the HTTP request
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                count++;
                if (count == 1) {
                    if (!requestHandler.checkRequest(line, paths)) {
                        ServerResponse.getBadRequest(outputStream);
                        clientSocket.close();
                        return;
                    }
                } else requestHandler.parseHeaders(line);
            }

            if (!requestHandler.checkHeaders()) {
                ServerResponse.getBadRequest(outputStream);
                clientSocket.close();
                return;
            }

            requestHandler.parseBody(reader);

            String responseBody = paths.get(requestHandler.getPathToResource())
                    .getFunction()
                    .handleFunction(requestHandler.getTextBody());

            ServerResponse.getOK(outputStream,responseBody);
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}