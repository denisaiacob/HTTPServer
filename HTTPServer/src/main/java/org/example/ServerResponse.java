package org.example;

import java.io.IOException;
import java.io.OutputStream;

public class ServerResponse {

    public static void getBadRequest(OutputStream outputStream) throws IOException {
        String response = "HTTP/1.1 400 Bad Request\r\n"
                + "\r\n";
        outputStream.write(response.getBytes());
    }

    public static void getOK(OutputStream outputStream) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n"
                + "\r\n";
        outputStream.write(response.getBytes());
    }
}
