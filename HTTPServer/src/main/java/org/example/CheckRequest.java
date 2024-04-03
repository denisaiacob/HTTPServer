package org.example;

import java.util.Arrays;
import java.util.List;

public class CheckRequest {
    public static boolean checkLine(int lineNumber, String lineContent) {
        if (lineNumber == 1) {
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
        }
        return true;
    }

    public static boolean checkPathToResource(String path) {
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
                        if(equalSplit.length ==1)
                            return false;
                        if (equalSplit.length >2)
                            return false;
                        if(Arrays.asList(equalSplit).contains(""))
                            return false;
                    }
                }
            }
        }
        return true;
    }
}
