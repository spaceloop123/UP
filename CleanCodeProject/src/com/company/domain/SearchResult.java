package com.company.domain;

import com.company.log.Level;
import com.company.log.Log;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private List<Message> list;
    private StringBuilder stringBuilder;

    public SearchResult(String className) {
        list = new ArrayList<>();
        stringBuilder = new StringBuilder(className);
    }

    public void add(Message message) {
        list.add(message);
    }

    public void log(Level level, String text) {
        String className = stringBuilder.toString();
        stringBuilder.append(": ")
                .append(text);
        Log.write(stringBuilder.toString(), level);
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append(className)
                .append(": Found: ")
                .append(list.size());
        Log.write(stringBuilder.toString(), level);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(message -> stringBuilder.append(message).append("\n"));
        return stringBuilder.toString();
    }
}
