package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class Handler {
    private Type itemsListType;
    private Gson gson;

    public Handler() {
        this.itemsListType = new TypeToken<List<Message>>() {
        }.getType();
        gson = new Gson();
    }

    public void readJSON(String fileName, History history) {
        try {
            Reader inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
            //List<Message> list = Collections.synchronizedList(gson.fromJson(new JsonReader(inputStreamReader), itemsListType));
            List<Message> list = gson.fromJson(new JsonReader(inputStreamReader), itemsListType);
            list.forEach(history::add);
            inputStreamReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File " + fileName + " not found");
        } catch (IOException e) {
            System.err.println("IOExeption while reading " + fileName);
        }
    }

    public void writeJSON(String fileName, History history) {
        try {
            Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName));
            //List<Message> listItems = Collections.synchronizedList(history.getList());
            List<Message> listItems = history.getList();
            gson.toJson(listItems, outputStreamWriter);
            outputStreamWriter.close();
        } catch (IOException e) {
            System.err.println("Can't write chat history to " + fileName);
        }
    }

    public void read(String fileName, History history) {
        Path path = Paths.get(".", fileName);
        try (Stream<String> line = Files.lines(path, Charset.defaultCharset())) {
            line.forEach(history::add);
        } catch (IOException e) {
            System.err.println("File " + fileName + " not found");
        }
    }

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