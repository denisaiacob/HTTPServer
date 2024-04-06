package main;

import org.example.HttpUserInterface;

import java.nio.file.InvalidPathException;

public class Main {
    public static void main(String[] args) {
        HttpUserInterface userInterface = new HttpUserInterface();
        try {
            userInterface.addGetPath("/a/b", () -> {
                System.out.println("Hello");

            });
            userInterface.addGetPath("/a/c", () -> {
                System.out.println("Hello");
            });
            userInterface.runServer(5001);
        } catch (InvalidPathException e) {
            System.out.println("The path has an incorrect format");
        }
    }
}
