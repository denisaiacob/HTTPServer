package org.example;

import java.nio.file.InvalidPathException;

public class HttpUserInterface extends Server {

    public void runServer(int PORT) {
        setPort(PORT);
        run();
    }

    private void checkPath(String path) {
        if (!RequestHandler.checkPathToResource(path)) {
            throw new InvalidPathException(path, "Incorrect path format");
        }
    }

    public void addGetPath(String path) {
        checkPath(path);
        addPath(path, "GET");
    }

    public void addPostPath(String path) {
        checkPath(path);
        addPath(path, "POST");
    }

    public void addPutPath(String path) {
        checkPath(path);
        addPath(path, "PUT");
    }

    public void addDeletePath(String path) {
        checkPath(path);
        addPath(path, "DELETE");
    }
}
