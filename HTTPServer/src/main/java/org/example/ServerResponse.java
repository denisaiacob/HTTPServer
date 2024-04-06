package org.example;

import java.io.IOException;
import java.io.OutputStream;

public class ServerResponse {

    public static void getBadRequest(OutputStream outputStream) throws IOException {
        String response = """
                HTTP/1.1 400 Bad Request\r
                \r
                """;
        outputStream.write(response.getBytes());
    }

    public static void getOK(OutputStream outputStream) throws IOException {
        String response = """
                HTTP/1.1 200 OK\r
                \r
                Am trimis datele\r
                """;
        outputStream.write(response.getBytes());
    }
}
