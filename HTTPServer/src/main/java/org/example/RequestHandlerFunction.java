package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;

@FunctionalInterface
public interface RequestHandlerFunction {
    String handleFunction(String body) throws IOException;
}
