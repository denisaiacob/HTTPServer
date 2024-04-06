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

    public void addGetPath(String path, RequestHandlerFunction function) {
        checkPath(path);
        addPath(path, new RequestAllowed("GET", path, function));
    }

    public void addPostPath(String path, RequestHandlerFunction function) {
        checkPath(path);
        addPath(path, new RequestAllowed("POST", path, function));
    }

    public void addPutPath(String path, RequestHandlerFunction function) {
        checkPath(path);
        addPath(path, new RequestAllowed("PUT", path, function));
    }

    public void addDeletePath(String path, RequestHandlerFunction function) {
        checkPath(path);
        addPath(path, new RequestAllowed("DELETE", path, function));
    }
}
