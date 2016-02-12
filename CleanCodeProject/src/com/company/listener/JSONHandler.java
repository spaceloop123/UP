package com.company.listener;

import com.company.domain.History;
import com.company.domain.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class JSONHandler implements Listener {
    private Type itemsListType;
    private Gson gson;

    public JSONHandler() {
        this.itemsListType = new TypeToken<List<Message>>() {
        }.getType();
        gson = new Gson();
    }

    @Override
    public void read(String fileName, History history) {
        try {
            Reader inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
            List<Message> list = Collections.synchronizedList(gson.fromJson(new JsonReader(inputStreamReader), itemsListType));
            list.forEach(history::add);
            inputStreamReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File " + fileName + " not found");
        } catch (IOException e) {
            System.err.println("IOExeption while reading " + fileName);
        }
    }

    @Override
    public void write(String fileName, History history) {
        try {
            Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName));
            List<Message> listItems = Collections.synchronizedList(history.getSortedList());
            gson.toJson(listItems, outputStreamWriter);
            outputStreamWriter.close();
        } catch (IOException e) {
            System.err.println("Can't write chat history to " + fileName);
        }
    }
}
