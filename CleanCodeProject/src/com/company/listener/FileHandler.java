package com.company.listener;

import com.company.domain.History;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class FileHandler implements Listener {
    @Override
    public void read(String fileName, History history) {
        Path path = Paths.get(".", fileName);
        try (Stream<String> line = Files.lines(path, Charset.defaultCharset())) {
            line.forEach(history::add);
        } catch (IOException e) {
            System.err.println("File " + fileName + " not found");
        }
    }

    @Override
    public void write(String fileName, History history) {
        Path path = Paths.get(".", fileName);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
            Files.createFile(path);
            Files.write(path, history.toString().getBytes(Charset.defaultCharset()), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Can't write chat history to " + fileName);
        }
    }
}
