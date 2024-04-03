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

            // Read the HTTP request
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                count++;
                if (count == 1) {
                    System.out.println(line);
                    System.out.println(CheckRequest.checkLine(count, line));
                }
            }

            // Respond with a simple HTTP response
            String response = "HTTP/1.1 200 OK\r\n"
                    + "\r\n";
            outputStream.write(response.getBytes());

            // Close the client socket
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}