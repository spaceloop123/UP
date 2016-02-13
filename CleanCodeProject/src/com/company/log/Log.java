package com.company.log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Log {
    private static final String LOG_FILE = "files/logfile.txt";
    private static final Path PATH = Paths.get(".", LOG_FILE);
    private static final DateFormat FORMATTER = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss] ");
    private static StringBuilder stringBuilder = new StringBuilder();

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

    public static void write(String logMessage, Level level) {
        try {
            stringBuilder.delete(0, stringBuilder.length());
            stringBuilder.append((FORMATTER.format(Date.from(Instant.now()))))
                    .append(level.toString())
                    .append(logMessage)
                    .append("\n");
            Files.write(PATH, stringBuilder.toString().getBytes(Charset.defaultCharset()), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Can't write chat log to " + LOG_FILE);
        }
    }
}
