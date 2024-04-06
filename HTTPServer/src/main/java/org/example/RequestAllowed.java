package org.example;

public class RequestAllowed {
    private final String method;
    private final String path;
    private final RequestHandlerFunction function;

    public RequestAllowed(String method, String path, RequestHandlerFunction function) {
        this.method = method;
        this.path = path;
        this.function = function;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public RequestHandlerFunction getFunction() {
        return function;
    }
}
