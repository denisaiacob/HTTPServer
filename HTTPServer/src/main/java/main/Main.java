package main;

import org.example.HttpUserInterface;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        HttpUserInterface userInterface = new HttpUserInterface();
        try {
            userInterface.addGetPath("/a/b", (body) -> {
                String file_path = "E:\\Desktop\\GitHub\\HTTPServer\\HTTPServer\\test\\index.html";
                String text = new String(Files.readAllBytes(Paths.get(file_path)), StandardCharsets.UTF_8);
                return text;
            });
            userInterface.addPostPath("/a/c", (body) -> {
                System.out.println(body);
                return "My function is /a/c";
            });

            userInterface.addGetPath("/index.html", (body) -> {
                return "Not Implemented";
            });
            userInterface.runServer(5001);
        } catch (InvalidPathException e) {
            System.out.println("The path has an incorrect format");
        }
    }
}
