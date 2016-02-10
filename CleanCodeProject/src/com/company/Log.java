package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Log {
    private static final String LOG_FILE = "files/logfile.txt";
    private static final Path PATH = Paths.get(".", LOG_FILE);

    static {
        try {
            if (Files.exists(PATH)) {
                Files.delete(PATH);
            }
            Files.createFile(PATH);
        } catch (IOException e) {
            System.err.println("Can't write chat log to " + LOG_FILE);
        }
    }

    public static void write(String logMessage) {
        try {
            logMessage = System.currentTimeMillis() + ": " + logMessage + "\n";
            Files.write(PATH, logMessage.getBytes(Charset.defaultCharset()), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Can't write chat log to " + LOG_FILE);
        }
    }
}
