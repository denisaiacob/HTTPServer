package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final int PORT = 5000;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);

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

    private static void handleRequest(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream outputStream = clientSocket.getOutputStream()) {
            RequestHandler requestHandler = new RequestHandler();

            // Read the HTTP request
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                count++;
                System.out.println(line);
                if (count == 1) {
                    if (!requestHandler.checkRequest(line)) {
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

            ServerResponse.getOK(outputStream);
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}