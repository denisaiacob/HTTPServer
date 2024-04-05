package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RequestHandler {
    private String method;
    private String pathToResource;
    private String httpVersion;

    private HashMap<String, String> headers = new HashMap<>();
    private String textBody;

    public boolean checkRequest(String lineContent) {
        List<String> methods = List.of("GET", "POST", "PUT", "DELETE");
        String[] splitContent = lineContent.split(" ");
        if (splitContent.length > 3)
            return false;
        if (!methods.contains(splitContent[0]))
            return false;
        if (!checkPathToResource(splitContent[1]))
            return false;
        if (!splitContent[2].matches("HTTP/\\d+\\.\\d+"))
            return false;

        setRequest(splitContent);
        return true;
    }

    private void setRequest(String[] splitContent) {
        this.method = splitContent[0];
        this.pathToResource = splitContent[1];
        this.httpVersion = splitContent[2];
    }

    private boolean checkPathToResource(String path) {
        if (path.length() == 1 && path.contains("/")) {
            return true;
        }
        String[] splitPath = path.split("/", -1);
        for (int i = 0; i < splitPath.length; i++) {
            if (splitPath[i].isEmpty() && i != 0)
                return false;

            if (i != splitPath.length - 1) {
                if (splitPath[i].contains("?") ||
                        splitPath[i].contains("&") ||
                        splitPath[i].contains("="))
                    return false;
            }

            if (i == splitPath.length - 1) {
                if ((splitPath[i].contains("&") || splitPath[i].contains("=")) &&
                        !splitPath[i].contains("?"))
                    return false;
                else {


                    String[] andSplit = splitPath[i].split("&", -1);
                    for (String s : andSplit) {
                        if (s.isEmpty()) {
                            return false;
                        }
                        String[] equalSplit = s.split("=", -1);
                        if (equalSplit.length == 1 &&
                                splitPath[i].contains("&"))
                            return false;
                        if (equalSplit.length > 2)
                            return false;
                        if (Arrays.asList(equalSplit).contains(""))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public void parseHeaders(String line) {
        String[] splitLine = line.split(":", -1);
        headers.put(splitLine[0], splitLine[1].trim());
    }

    public boolean checkHeaders() {
        if (!headers.containsKey("Host") ||
                headers.get("Host") == null) {
            return false;
        }
        return true;
    }

    public String getBodyLength() {
        return headers.getOrDefault("Content-Length", null);
    }

    private void setBody(String body) {
        this.textBody = body;
    }

    public void parseBody(BufferedReader reader) throws IOException {
        int bodyLength = Integer.parseInt(getBodyLength());
        if (bodyLength != 0) {
            char[] buf = new char[bodyLength];
            reader.read(buf, 0, bodyLength);
            setBody(String.valueOf(buf));
            System.out.println(textBody);
        }
    }
}
